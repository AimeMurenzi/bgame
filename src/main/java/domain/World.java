/**
 * @Author: Aimé
 * @Date:   2021-04-11 03:50:17
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-11 21:18:31
 */
package domain;

import java.util.HashMap;
import java.util.Map;

public class World {
    private static class HoldInstance {
        private static final World INSTANCE = new World();
    }

    public static World getInstance() {
        return HoldInstance.INSTANCE;
    }

    //id,building
    private Map<Integer,Building> worldBuilding=new HashMap<>();
    private Map<Integer,Player> players=new HashMap<>();
    private void initWorld(){}

    
}
