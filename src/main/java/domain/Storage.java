/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:38:53
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-08 06:03:42
 */
package domain;

import java.util.HashMap;
import java.util.Map;

public class Storage  extends Building{ 
    private final Map<Integer, Integer> storageLevelMap=new HashMap<>(){ 
        private static final long serialVersionUID = 1L; 
    {
        put(1, 8000);
        put(2, 16000);
        put(3, 24000);
        put(4, 32000);
        put(5, 40000);
        put(6, 48000);
        put(7, 56000);
        put(8, 64000);
        put(9, 72000);
        put(10, 80000);
    }}; 
    private void initUpgradeRequirements(){
        setUpgradeResourceRequirementsMap(BGameSettings.getUpgradeResourceRequirementsMapFor(ResourceType.STORAGE));
        setUpgradeBuildingRequirementsMap(BGameSettings.getUpgradeBuildingRequirementsMapFor(ResourceType.STORAGE));

    }
    public Storage(long parentId){
        super(parentId);
       initUpgradeRequirements();
       }
    public Storage(ICapital parentCapital){
        super(parentCapital);
        initUpgradeRequirements();
      }
    @Override
    public ResourceType getResourceType() { 
        return ResourceType.STORAGE;
    }

    public Map<Integer, Integer> getStorageLevelMap() {
        return storageLevelMap;
    }
}
