/**
 * @Author: Aimé
 * @Date:   2021-04-11 03:50:17
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:51:46
 */
package domain.world;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import domain.Util;
import domain.buildings.Building;
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
    private final Map<Long, Building> worldBuildings = new HashMap<>();
    private final Map<Player, Player> players = new HashMap<>();// why? speed

    public static  Map<Long, Building> getWorldBuildings() { 
        return getInstance().worldBuildings;
    }

    public static Map<Player, Player> getPlayers() {
        return new HashMap<>(getInstance().players);
    }

    /**
     * generates a guaranteed non existing building id
     * 
     * @return generated building id
     */
    private long generateBuildingID() {
        long id = Util.generateID();
        while (worldBuildings.containsKey(id)) {
            id = Util.generateID();
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
        final Map<Long, Building> worldBuildings = getInstance().worldBuildings;
        building.setId(getInstance().generateBuildingID());
        worldBuildings.put(building.getId(), building);
    }

    /**
     * adds building. building id if given is ignored and a new unique id is
     * generated
     * 
     * @param building
     */
    public static void addBuilding(Building building) {
        final Map<Long, Building> worldBuildings = getInstance().worldBuildings;
        building.setId(getInstance().generateBuildingID());
        worldBuildings.put(building.getId(), building);
    }

    public static void addPlayer(Player player) {
        Map<Player, Player> players = getInstance().players;
        if (players.containsKey(player)) {
            Util.throwBadRequest("Player name already taken");
        }
        players.put(player, player);
    }
}
