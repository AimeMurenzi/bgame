/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:10:23
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-08 07:07:16
 */
package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
/**
 * Capital
 */
public class Capital extends Building implements ICapital {

    private final Map<ResourceType, Integer> storedResources = new HashMap<>();
    private final Map<ResourceType, List<Building>> capitalBuildings = new HashMap<>();

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
        super(IDGenerator.increment(), IDGenerator.increment());
        initCapital();
    }

    public Capital(long parentId) {
        super(parentId);
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

    public Map<ResourceType, Integer> getStoredResources() {
        return storedResources;
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
        List<Building> productionBuildings = capitalBuildings.get(ResourceType.MATPRODUCER);
        List<Building> storageBuildings = capitalBuildings.get(ResourceType.STORAGE);
        int maximumStorageCapacity = 2000;
        if (storageBuildings != null)
            for (Building building : storageBuildings) {
                Storage storage = (Storage) building;
                maximumStorageCapacity += storage.getStorageLevelMap().getOrDefault(storage.getLevel(), 0);
            }
        if (productionBuildings != null)
            for (Building building : productionBuildings) {
                BuildMaterialProducer buildMaterial = (BuildMaterialProducer) building;
                ResourceType resourceTypeProduced = buildMaterial.getResourceTypeProduced();
                int currentlyStored = storedResources.getOrDefault(resourceTypeProduced, 0);
                currentlyStored += buildMaterial.unload();
                currentlyStored = currentlyStored > maximumStorageCapacity ? maximumStorageCapacity : currentlyStored;
                storedResources.put(buildMaterial.getResourceTypeProduced(), currentlyStored);
            }
    }

    public Map<String, Object> getInfo() {
        refresh();
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("level", getLevel());
                put("storedResources", storedResources);
                put("buildings", capitalBuildings);
            }
        };

    }

    @Override
    public Map<String, Map<ResourceType, Integer>> build(Building building) {

        Map<String, Map<ResourceType, Integer>> missingResourcesMap = upgrade(building);
        if (missingResourcesMap.size() == 0) {
            ResourceType resourceType = building.getResourceType();
            List<Building> buildingsOfResourceType = capitalBuildings.getOrDefault(resourceType, new ArrayList<>());
            buildingsOfResourceType.add(building);
            capitalBuildings.put(resourceType, buildingsOfResourceType);
        }

        return missingResourcesMap;
    }

}
