/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:44:34
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 04:05:43
 */
package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.buildings.BuildMaterialProducer;
import domain.buildings.Building;
import domain.buildings.Capital;
import domain.buildings.IUpgradeable;

public class CapitalTest {
    private static Capital capital;

    @BeforeClass
    public static void initOnce() {
        capital = new Capital();
    }

    private IUpgradeable upgradeable;

    @Before
    public void initPerTest() {
        capital.getStoredResources().put(ResourceType.BUILDMAT1, 2000);
        capital.getStoredResources().put(ResourceType.BUILDMAT2, 2000);
        capital.getStoredResources().put(ResourceType.BUILDMAT3, 2000);
        capital.getCapitalBuildings().getOrDefault(ResourceType.MATPRODUCER, new ArrayList<>()).clear();
        upgradeable = new IUpgradeable() {
            private int level = 1;

            @Override
            public Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMap() {
                return BGameSettings.getUpgradeResourceRequirementsMapFor(ResourceType.BUILDMAT1);
            }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
            }

            @Override
            public ResourceType getResourceType() {
                return null;
            }

            @Override
            public Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> getUpgradeBuildingRequirementsMap() {
                return null;
            }
        };
    }

    @Test
    public void upgradeRequirementsNotMet() {

        capital.getStoredResources().put(ResourceType.BUILDMAT1, 0);
        capital.getStoredResources().put(ResourceType.BUILDMAT2, 0);
        capital.getStoredResources().put(ResourceType.BUILDMAT3, 0);

        Map<String, Map<ResourceType, Integer>> allMissingRequirements= capital.upgrade(upgradeable);
        assertEquals(1, upgradeable.getLevel());
        assertNotEquals(0, allMissingRequirements.size());
        Map<ResourceType, Integer> upgradeRequirementsForLevel1 = upgradeable.getUpgradeResourceRequirementsMap()
                .get(1);
        assertEquals(1200, upgradeRequirementsForLevel1.get(ResourceType.BUILDMAT1).intValue());
        assertEquals(400, upgradeRequirementsForLevel1.get(ResourceType.BUILDMAT2).intValue());
        assertEquals(400, upgradeRequirementsForLevel1.get(ResourceType.BUILDMAT3).intValue());
    }

    @Test
    public void upgradeRequirementsMet() { 
        Map<String, Map<ResourceType, Integer>> allMissingRequirements= capital.upgrade(upgradeable);
        Map<ResourceType, Integer> storedResources = capital.getStoredResources();
        assertEquals(2, upgradeable.getLevel());
        assertEquals(0, allMissingRequirements.size());
        assertEquals(800, storedResources.get(ResourceType.BUILDMAT1).intValue());
        assertEquals(1600, storedResources.get(ResourceType.BUILDMAT2).intValue());
        assertEquals(1600, storedResources.get(ResourceType.BUILDMAT3).intValue());
    }
    @Test
    public void testBuild(){ 
        BuildMaterialProducer buildMaterial=new BuildMaterialProducer(capital,ResourceType.BUILDMAT1);
        Map<String, Map<ResourceType, Integer>> allMissingRequirements=capital.build(buildMaterial);
        Map<ResourceType, Integer> storedResources = capital.getStoredResources();
        assertEquals(2, buildMaterial.getLevel());
        assertEquals(0, allMissingRequirements.size());
        assertEquals(800, storedResources.get(ResourceType.BUILDMAT1).intValue());
        assertEquals(1600, storedResources.get(ResourceType.BUILDMAT2).intValue());
        assertEquals(1600, storedResources.get(ResourceType.BUILDMAT3).intValue());
    }
    @Test
    public void testBuildingRequirementsNotMet(){ 
        upgradeable = new IUpgradeable() {
            private int level = 1;

            @Override
            public Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMap() {
                return null; }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
            }

            @Override
            public ResourceType getResourceType() {
                return null;
            }

            @Override
            public Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> getUpgradeBuildingRequirementsMap() {
                return BGameSettings.getUpgradeBuildingRequirementsMapFor(ResourceType.STORAGE);
            }
        };
 
        List<Building> materialProducers= capital.getCapitalBuildings().get(ResourceType.MATPRODUCER); 
        if(materialProducers!=null){
            materialProducers.clear();
        }
        
        Map<String, Map<ResourceType, Integer>> allMissingRequirements=capital.upgrade(upgradeable);  
        
        assertNotEquals(0, allMissingRequirements.size()); 
        assertEquals(1, upgradeable.getLevel());
    }
    @Test
    public void testBuildingRequirementsMet(){ 
        upgradeable = new IUpgradeable() {
            private int level = 1;

            @Override
            public Map<Integer, Map<ResourceType, Integer>> getUpgradeResourceRequirementsMap() {
                return null; }

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
            }

            @Override
            public ResourceType getResourceType() {
                return null;
            }

            @Override
            public Map<Integer, Map<ResourceType, Map<ResourceType, Integer>>> getUpgradeBuildingRequirementsMap() {
                return BGameSettings.getUpgradeBuildingRequirementsMapFor(ResourceType.STORAGE);
            }
        };
        
        List<Building> materialProducers= capital.getCapitalBuildings().get(ResourceType.MATPRODUCER); 
        if(materialProducers!=null){
            materialProducers.clear();
        }else{
            materialProducers=new ArrayList<>();
            capital.getCapitalBuildings().put(ResourceType.MATPRODUCER, materialProducers);
        }
        
        materialProducers.add(new BuildMaterialProducer(capital, ResourceType.BUILDMAT1));
        materialProducers.add(new BuildMaterialProducer(capital,ResourceType.BUILDMAT2));
        materialProducers.add(new BuildMaterialProducer(capital,ResourceType.BUILDMAT3));
        
        Map<String, Map<ResourceType, Integer>> allMissingRequirements=capital.upgrade(upgradeable);  
        
        assertEquals(0, allMissingRequirements.size()); 
        assertEquals(1, upgradeable.getLevel());
    }
}
