/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:10:23
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 22:17:03
 */
package domain.buildings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ws.rs.ext.Provider;

import domain.BGameSettings;
import domain.ResourceType;
import domain.Util;
import domain.players.Player;

@Provider
@Singleton
/**
 * Capital
 */
public class Capital extends Building implements ICapital {

    private final Map<ResourceType, Integer> storedResources = new HashMap<>();
    private final Map<ResourceType, List<Building>> capitalBuildings = new HashMap<>();
    private final Map<String, Building> capitalBuildingRecord = new HashMap<>();
    private String capitalName;
    private int maxStorageCapacity = 2000;

    @Override
    public Building copy() {
        Capital capital = new Capital();
        capital.setParentId(this.getParentId());
        capital.setId(this.getId());
        capital.setLevel(this.getLevel());

        capital.capitalName = new String(this.capitalName);
        capital.storedResources.clear();
        capital.capitalBuildings.clear();
        capital.capitalBuildingRecord.clear();
        capital.storedResources.putAll(storedResources);
        capital.capitalBuildings.putAll(capitalBuildings);
        capital.capitalBuildingRecord.putAll(capitalBuildingRecord);

        return capital;
    }

    private String generateBuildingID() {
        String id = Util.generateYouTubeStyleNameID();
        while (capitalBuildingRecord.containsKey(id)) {
            id = Util.generateYouTubeStyleNameID();
        }
        return id;
    }

    private void initCapital() {
        storedResources.put(ResourceType.BUILDMAT1, 2000);
        storedResources.put(ResourceType.BUILDMAT2, 2000);
        storedResources.put(ResourceType.BUILDMAT3, 2000);
        setUpgradeResourceRequirementsMap(BGameSettings.getUpgradeResourceRequirementsMapFor(ResourceType.CAPITAL));
    }

    // private int maximumStorageCapacity = 2000;//TODO: to be used later for
    // keeping recalculated capital storage size(to save on processing power and
    // reducing call delay).
    public Capital() {
        super("0");
        initCapital();
    }

    public Capital(String parentId, String name) {
        super(parentId);
        this.capitalName = name;
        initCapital();
    }

    public Map<String, Map<ResourceType, Integer>> upgrade(IUpgradeable upgradeable) {
        refresh();
        Map<ResourceType, Integer> capitalStoredResources = this.getStoredResources();
        Map<ResourceType, List<Building>> capitalBuildings = this.getCapitalBuildings();

        Map<String, Map<ResourceType, Integer>> allMissingResources = new HashMap<>();

        Map<Integer, Map<ResourceType, Integer>> upgradeResourceRequirementsMap = upgradeable
                .getUpgradeResourceRequirementsMap();
        Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> upgradeBuildingRequirementsMap = upgradeable
                .getUpgradeBuildingRequirementsMap();

        Map<ResourceType, Integer> missingResourceRequirementsMap = new HashMap<>();
        Map<ResourceType, Integer> missingBuildingLevelRequirementsMap = new HashMap<>();
        if (upgradeBuildingRequirementsMap != null) {
            // level
            Map<ResourceType, Map<ResourceType, Integer>> buildingRequirementsForLevel = upgradeBuildingRequirementsMap
                    .get(upgradeable.getLevel());
            // per category
            if (buildingRequirementsForLevel != null)
                // building are stored in this format
                /*
                 * --- category:ResourceType --- building , requiredLevel :Map<ResourceType,
                 * Integer>
                 * 
                 */

                for (Map.Entry<ResourceType, Map<ResourceType, Integer>> buildingRequirementsForLevelEntry : buildingRequirementsForLevel
                        .entrySet()) {
                    ResourceType buildingCategory = buildingRequirementsForLevelEntry.getKey();
                    Map<ResourceType, Integer> buildingUpgradeRequirement = buildingRequirementsForLevelEntry
                            .getValue();
                    List<Building> capitalBuildingCategoryList = capitalBuildings.get(buildingCategory);
                    if (capitalBuildingCategoryList != null && capitalBuildingCategoryList.size() > 0) {

                        // for keeping track of missing buildings
                        Map<ResourceType, Boolean> buildingRequirementMetBooleanMap = new HashMap<>();
                        for (ResourceType resourceType : buildingUpgradeRequirement.keySet()) {
                            buildingRequirementMetBooleanMap.put(resourceType, false);
                        }
                        for (Building capitalBuilding : capitalBuildingCategoryList) {
                            ResourceType capitalBuildingResourceType = capitalBuilding.getResourceType();
                            if (capitalBuildingResourceType == ResourceType.MATPRODUCER) {
                                ResourceType convolutedResource = ((BuildMaterialProducer) capitalBuilding)
                                        .getResourceTypeProduced();

                                Boolean reqMet = buildingRequirementMetBooleanMap.get(convolutedResource);
                                if (reqMet != null) {
                                    /*
                                     * if true then we have already met the requirement. this is here for the case
                                     * we have multiple buildings of the same kind and one of them has met the
                                     * required level
                                     */
                                    if (!buildingRequirementMetBooleanMap.get(convolutedResource)) {
                                        Integer requiredLevel = buildingUpgradeRequirement.get(convolutedResource);
                                        if (capitalBuilding.getLevel() >= requiredLevel) {
                                            buildingRequirementMetBooleanMap.put(convolutedResource, true);
                                            missingBuildingLevelRequirementsMap.remove(convolutedResource);
                                        } else {
                                            missingBuildingLevelRequirementsMap.put(convolutedResource, requiredLevel);
                                        }
                                    }
                                }

                            } else {
                                Boolean reqMet = buildingRequirementMetBooleanMap.get(capitalBuildingResourceType);
                                if (reqMet != null) {
                                    if (!buildingRequirementMetBooleanMap.get(capitalBuildingResourceType)) {
                                        Integer requiredLevel = buildingUpgradeRequirement
                                                .get(capitalBuildingResourceType);
                                        if (capitalBuilding.getLevel() >= requiredLevel) {
                                            buildingRequirementMetBooleanMap.put(capitalBuildingResourceType, true);
                                            missingBuildingLevelRequirementsMap.remove(capitalBuildingResourceType);
                                        } else {
                                            missingBuildingLevelRequirementsMap.put(capitalBuildingResourceType,
                                                    requiredLevel);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        missingBuildingLevelRequirementsMap.putAll(buildingUpgradeRequirement);
                    }
                }
        }
        Map<ResourceType, Integer> requirementsForLevel = null;
        if (upgradeResourceRequirementsMap != null) {
            // current task: make sure i can require a building level
            // to update
            // looking into decorator pattern
            requirementsForLevel = upgradeResourceRequirementsMap.get(upgradeable.getLevel());

            boolean resourceRequirementsMet = true;
            for (Map.Entry<ResourceType, Integer> requirementsForLevelEntry : requirementsForLevel.entrySet()) {
                ResourceType resourceType = requirementsForLevelEntry.getKey();
                Integer requiredAmount = requirementsForLevelEntry.getValue();
                Integer availableInStorage = capitalStoredResources.getOrDefault(resourceType, 0);
                int missingAmount = availableInStorage - requiredAmount;
                if (missingAmount < 0) {
                    missingResourceRequirementsMap.put(resourceType, Math.abs(missingAmount));
                    if (resourceRequirementsMet) {
                        resourceRequirementsMet = false;
                    }
                }
            }
        }
        if (missingResourceRequirementsMap.size() != 0) {
            allMissingResources.put("missingResourceRequirements", missingResourceRequirementsMap);
        }
        if (missingBuildingLevelRequirementsMap.size() != 0) {
            allMissingResources.put("missingBuildingLevelRequirements", missingBuildingLevelRequirementsMap);
        }
        if (allMissingResources.size() == 0 && requirementsForLevel != null) {
            for (Map.Entry<ResourceType, Integer> entry : requirementsForLevel.entrySet()) {
                ResourceType resourceType = entry.getKey();
                Integer requiredAmount = entry.getValue();
                Integer availableInStorage = capitalStoredResources.get(resourceType);
                capitalStoredResources.put(resourceType, availableInStorage - requiredAmount);
            }
            upgradeable.setLevel(upgradeable.getLevel() + 1);
        }
        return allMissingResources;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public Map<ResourceType, Integer> getStoredResources() {
        return storedResources;
    }

    public int getMaxStorageCapacity() {
        return maxStorageCapacity;
    }

    public void setMaxStorageCapacity(int maxStorageCapacity) {
        this.maxStorageCapacity = maxStorageCapacity;
    }

    public Map<ResourceType, List<Building>> getCapitalBuildings() {
        return capitalBuildings;
    }

    // public void setStoredResources(Map<ResourceType, Integer> storedResources) {
    // this.storedResources = storedResources;
    // }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.CAPITAL;
    }

    public void refresh() {

        refreshMaxStorage();
        refreshProduction();
    }

    @Override
    public Map<String, Object> getInfo() {
        return getInfo(null);
    }

    /**
     * get inf about Capital
     * 
     * @param player
     * @return limited capital information depending on whether the player is owner
     *         of this capital
     */
    public Map<String, Object> getInfo(final Player player) {
        refresh();
        Map<String, Object> ownerInfo = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("id", getId());
                put("capitalName", getCapitalName());
                put("level", getLevel());
                put("storedResources", storedResources);
                put("buildings", capitalBuildings);
            }
        };
        if (player != null && this.getParentId().equals(player.getId())) {
            return ownerInfo;
        } else {
            return new HashMap<String, Object>() {
                {
                    put("id", getId());
                    put("capitalName", getCapitalName());
                    put("level", getLevel());
                }
            };
        }
    }

    @Override
    public Map<String, Map<ResourceType, Integer>> build(Building building) {
        capitalOffLimitCheck(building);
        Map<String, Map<ResourceType, Integer>> missingResourcesMap = upgrade(building);
        if (missingResourcesMap.size() == 0) {
            building.setId(generateBuildingID());
            building.setParentId(this.getId());
            ResourceType resourceType = building.getResourceType();
            List<Building> buildingsOfResourceType = capitalBuildings.getOrDefault(resourceType, new ArrayList<>());
            buildingsOfResourceType.add(building);
            capitalBuildingRecord.put(building.getId(), building);
            capitalBuildings.put(resourceType, buildingsOfResourceType);
        }

        return missingResourcesMap;
    }

    private void capitalOffLimitCheck(Building building) {
        if (building.getResourceType().equals(ResourceType.CAPITAL)) {
            Util.throwBadRequest("Capital Can Not Build Capital");
        }
    }
    public void demolish(Building building) {
        capitalOffLimitCheck(building);
        Map<ResourceType, Integer> resourcesForLevel=building.getUpgradeResourceRequirementsMap().get(1);
                   for(Map.Entry<ResourceType, Integer> resource:resourcesForLevel.entrySet())
                   addResource(resource.getKey(),resource.getValue());

    }
    public void addResource(Map<ResourceType, Integer> resource) {
        for (Map.Entry<ResourceType, Integer> entry : resource.entrySet()) {
            addResource(entry.getKey(), entry.getValue());
        }
    }

    private void addResource(ResourceType key, Integer value) {
        int currentlyStored = storedResources.getOrDefault(key, 0);
        currentlyStored += value;
        currentlyStored = currentlyStored > getMaxStorageCapacity() ? getMaxStorageCapacity() : currentlyStored;
        storedResources.put(key, currentlyStored);
    }

    private void refreshProduction() {
        List<Building> productionBuildings = capitalBuildings.get(ResourceType.MATPRODUCER);
        if (productionBuildings != null)
            for (Building building : productionBuildings) {
                BuildMaterialProducer buildMaterial = (BuildMaterialProducer) building;
                addResource(buildMaterial.getResourceTypeProduced(), buildMaterial.unload());
            }
    }

    private void refreshMaxStorage() {
        List<Building> storageBuildings = capitalBuildings.get(ResourceType.STORAGE);
        int maxStorageCapacity = 2000;
        if (storageBuildings != null)
            for (Building building : storageBuildings) {
                Storage storage = (Storage) building;
                maxStorageCapacity += storage.getStorageLevelMap().getOrDefault(storage.getLevel(), 0);
            }
        setMaxStorageCapacity(maxStorageCapacity);
    }

}
