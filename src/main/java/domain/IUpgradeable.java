/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:16:20
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-07 04:16:39
 */
package domain;

import java.util.Map;

public interface IUpgradeable {

    Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMap();
    /**
     * layer1= level, resource category ex. MATPRODUCER
     * layer2= resource category, building type resource required ex. BUILDMAT3
     * layer3= building type resource required, resource required level 
     * 
     * @return
     */
    Map</*level */Integer, /*category*/Map</*categoryType*/ResourceType, Map</*requiredBuildingType*/ResourceType, /*requiredBuildingLevel*/Integer>>> getUpgradeBuildingRequirementsMap();

    int getLevel();

    void setLevel(int level);
    /**
     * returns what category this resource belongs to ex. MATPRODUCER, STORAGE ...
     * @return
     */
    ResourceType getResourceType();
    
}