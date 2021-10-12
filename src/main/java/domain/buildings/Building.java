/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:37:36
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:40:07
 */
package domain.buildings;

import java.util.Map;

import domain.ResourceType;

public abstract class Building implements IUpgradeable {
    private String id;
    private String parentId;
    private int level = 1;
    private Map<Integer, Map<ResourceType, Integer>> upgradeResourceRequirementsMap;

    private Map</* level */Integer, /* category */Map</* categoryType */ResourceType, Map</* requiredBuildingType */ResourceType, /*
                                                                                                                                   * requiredBuildingLevel
                                                                                                                                   */Integer>>> upgradeBuildingRequirementsMap;

    public Building(String parentId) {
        this.parentId = parentId;
    }

    public Building(ICapital parentCapital) {
        this.parentId = parentCapital.getId();
    }

    public Building(String parentId, String id) {
        this.parentId = parentId;
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMap() {
        return upgradeResourceRequirementsMap;
    }

    public void setUpgradeResourceRequirementsMap(
            Map<Integer, Map<ResourceType, Integer>> upgradeResourceRequirementsMap) {
        this.upgradeResourceRequirementsMap = upgradeResourceRequirementsMap;
    }

    public Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> getUpgradeBuildingRequirementsMap() {
        return upgradeBuildingRequirementsMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setUpgradeBuildingRequirementsMap(
            Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> upgradeBuildingRequirementsMap) {
        this.upgradeBuildingRequirementsMap = upgradeBuildingRequirementsMap;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Building other = (Building) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public abstract Building copy();
}