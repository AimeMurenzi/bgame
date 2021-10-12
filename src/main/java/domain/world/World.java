/**
 * @Author: Aimé
 * @Date:   2021-04-11 03:50:17
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 20:56:40
 */
package domain.world;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import domain.ResourceType;
import domain.Util;
import domain.buildings.Building;
import domain.buildings.Capital;
import domain.players.Player;

@Provider
public class World {
    private static class HoldInstance {
        private static final World INSTANCE = new World();
    }

    public static World getInstance() {
        return HoldInstance.INSTANCE;
    }

    // TODO to be replaced by database
    private Map<String, Building> worldBuildings;
    private Map<Player, Player> players;// why? speed

    private World() {
        this.worldBuildings = new HashMap<>();
        this.players = new HashMap<>();
    }

    public static Map<String, Building> getWorldBuildings() {
        return new HashMap<>(getInstance().worldBuildings);
    }

    public static Map<Player, Player> getPlayers() {
        return new HashMap<>(getInstance().players);
    }

    /**
     * generates a guaranteed non existing building id
     * 
     * @return generated building id
     */
    private String generateBuildingID() {
        String id = Util.generateYouTubeStyleNameID();
        while (worldBuildings.containsKey(String.valueOf(id))) {
            id = Util.generateYouTubeStyleNameID();
        }
        return id;
    }

    /**
     * adds building. building id if given is ignored and a new unique id is
     * generated
     * 
     * @param building
     */
    public static void updateBuilding(Building building) {
        final Map<String, Building> worldBuildings = getInstance().worldBuildings;
        building.setId(getInstance().generateBuildingID());
        worldBuildings.put(String.valueOf(building.getId()), building);
    }

    /**
     * adds building. building id if given is ignored and a new unique id is
     * generated
     * 
     * @param building
     */
    public static void addBuilding(Building building) {
        building.setId(getInstance().generateBuildingID());
        String id = String.valueOf(building.getId());
        Map<String, Building> worldBuildings = getInstance().worldBuildings;
        worldBuildings.put(id, building.copy());
        // worldBuildings.put(id, building);
        // worldBuildings.put(id, building);
        // worldBuildings.put(id, building);
        // worldBuildings.put(id, building);
        Building building2 = worldBuildings.get(id);
        if (building.equals(building2)) {
            System.out.println("EQUALS");
        } else {
            System.out.println("NOT EQUALS");
        }
        assertEquals(building, building2);
        return;
    }

    public static void addPlayer(Player player) {
        Map<Player, Player> players = getInstance().players;
        if (players.containsKey(player)) {
            Util.throwBadRequest("Player name already taken");
        }
        players.put(player, player);
    }

    public static Map<String, Map<ResourceType, Integer>> build(String capitalId, Building building, Player player) {
        Player playerLocal = getPlayers().get(player);
        if (playerLocal != null) {
            final Map<String, Building> worldBuildings = World.getWorldBuildings();
            Building playersCapital = worldBuildings.get(capitalId);
            if (playersCapital != null && playersCapital instanceof Capital
                    && playersCapital.getParentId().equals(playerLocal.getId())) {
                Capital capital = (Capital) playersCapital;
                return capital.build(building);// returns missing resource if there are any
            }
            Util.throwBadRequest("Capital Does Not Belong To Player");
            // get player capital
            // check if player has path param capital
            // build building in capital
        }
        Util.throwBadRequest("Player Does Not Exist");
        return null;
    }

    private void demolish(String buildingId, Player player) {
        Player playerLocal = getPlayers().get(player);
        if (playerLocal != null) {
            final Map<String, Building> worldBuildings = World.getWorldBuildings();
            Building building = worldBuildings.get(buildingId);
            if (building != null && !(building instanceof Capital)) {
                Capital capital = (Capital) worldBuildings.get(building.getParentId());
                if (capital.getParentId().equals(player.getId())) {
                    capital.demolish(building); 
                }
            } else if (building != null && (building instanceof Capital)
                    && building.getParentId() == playerLocal.getId()) {
                worldBuildings.remove(buildingId);
            }
            Util.throwBadRequest("Capital Does Not Belong To Player");
            // get player capital
            // check if player has path param capital
            // build building in capital
        }
    }
}
