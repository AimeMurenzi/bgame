/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:17:30
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:49:38
 */
package domain.buildings;

import domain.BGameSettings;
import domain.ResourceType;

public class BuildMaterialProducer extends Building {
    private long lastUpdate;
    private int produced = 0;
    private final int PRODUCTIONPERSECOND = 15;
    private final ResourceType resourceTypeProduced;

    public BuildMaterialProducer(String parentId, ResourceType resourceTypeProduced) {
        super(parentId);
        lastUpdate = System.currentTimeMillis();
        this.resourceTypeProduced = resourceTypeProduced;
        setUpgradeResourceRequirementsMap(BGameSettings.getUpgradeResourceRequirementsMapFor(resourceTypeProduced));

    }

    public BuildMaterialProducer(ICapital parentCapital, ResourceType resourceTypeProduced) {
        super(parentCapital);
        lastUpdate = System.currentTimeMillis();
        this.resourceTypeProduced = resourceTypeProduced;
        setUpgradeResourceRequirementsMap(BGameSettings.getUpgradeResourceRequirementsMapFor(resourceTypeProduced));

    }

    private void calculateProduced() {
        long currentTime = System.currentTimeMillis();
        long lapsedMilliseconds = currentTime - lastUpdate;
        if (lapsedMilliseconds > 0) {
            long lapsedSeconds = lapsedMilliseconds / 1000;
            produced += (int) (PRODUCTIONPERSECOND * getLevel() * lapsedSeconds);
        }
        lastUpdate = currentTime;
    }

    @Override
    public void setLevel(int level) {
        calculateProduced();
        super.setLevel(level);
    }

    public int unload() {
        calculateProduced();
        int tmp = produced;
        produced = 0;
        return tmp;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.MATPRODUCER;
    }

    public ResourceType getResourceTypeProduced() {
        return resourceTypeProduced;
    }

    @Override
    public Building copy() { 
        return null;
    }

}