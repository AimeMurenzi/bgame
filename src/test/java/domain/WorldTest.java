/**
 * @Author: Aimé
 * @Date:   2021-06-20 00:29:39
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:59:33
 */
package domain;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import domain.buildings.Building;
import domain.buildings.Capital;
import domain.world.World;
public class WorldTest { 
    @BeforeClass
    public static void initOnce(){
        
    }
    @Test
    public void addCapitalTest() {
        int coordinateX=5,coordinateY=5;
        String parentID="1l";
        Capital newCapital=new Capital(parentID,String.format("Capital[%d,%d]", coordinateX,coordinateY));  
        World.addBuilding(newCapital);  
        Map<String, Building> worldBuildings=World.getWorldBuildings();
        HashMap<String, Building> w=new HashMap<>();
        w.put(String.valueOf(newCapital.getId()), newCapital);
        assertEquals(newCapital, World.getWorldBuildings().get(String.valueOf(newCapital.getId())));
    }
}
