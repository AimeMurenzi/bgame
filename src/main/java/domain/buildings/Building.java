/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:37:36
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 04:12:13
 */
package domain.buildings;

import java.util.Map;

import domain.RandomIDGenerator;
import domain.ResourceType;

public abstract class Building implements IUpgradeable {
    private long id;
    private long parentId;
    private int level = 1;
    private Map<Integer, Map<ResourceType, Integer>> upgradeResourceRequirementsMap; 

    private Map</*level */Integer, /*category*/Map</*categoryType*/ResourceType, Map</*requiredBuildingType*/ResourceType, /*requiredBuildingLevel*/Integer>>> upgradeBuildingRequirementsMap;
    
    private void generateID(){
        this.id=RandomIDGenerator.generateID();
    }
    public Building(long parentId){
        this.parentId=parentId;
        generateID();
    }
    public Building(ICapital parentCapital){
        this.parentId=parentCapital.getId();
        generateID();
    }
    public Building(long parentId,long id){
        this.parentId=parentId;
        this.id=id;
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
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getParentId() {
        return parentId;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    public void setUpgradeBuildingRequirementsMap(
            Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> upgradeBuildingRequirementsMap) {
        this.upgradeBuildingRequirementsMap = upgradeBuildingRequirementsMap;
    }
}