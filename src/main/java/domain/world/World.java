/**
 * @Author: Aimé
 * @Date:   2021-04-11 03:50:17
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 04:12:34
 */
package domain.world;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.Provider;

import domain.Util;
import domain.buildings.Building;
import domain.players.Player2;
@Provider
public class World implements IWorld{
    private static class HoldInstance {
        private static final World INSTANCE = new World();
    }

    public static World getInstance() {
        return HoldInstance.INSTANCE;
    }

    private final Map<Long, Object> worldRecord = new HashMap<>(); 
    private final Map<Long, Building> worldBuildings = new HashMap<>();
    private final Map<Long, Player2> playerAccounts = new HashMap<>();//why? speed
    private final Map<Player2, Player2> players = new HashMap<>();//why? speed

    public Map<Long, Building> getWorldBuildings() {
        return getInstance().worldBuildings;
    }

    public static Map<Player2, Player2> getPlayers() {
        return new HashMap<>(getInstance().players);
    }


    private void initWorld() {
    }
    

    private boolean addToWorldRecord(Long id, Object object) {
        Map<Long, Object> worldRecord = getWorldRecord();
        if (worldRecord.containsKey(id))
            return false;
        else
            worldRecord.put(id, object);
        return true;
    }

    public static boolean add(Building building) {
        if (getInstance().addToWorldRecord(building.getId(), building)) {
            getInstance().worldBuildings.put(building.getId(), building);
            return true;
        }
        return false;
    } 
    public static void addPlayer(Player2 player){
        Map<Player2, Player2> players=getInstance().players;
        if (players.containsKey(player)) {
            Util.throwBadRequest("Player name already taken"); 
        }
        players.put(player, player);
    }

    public static Map<Long, Object> getWorldRecord() {
        return getInstance().worldRecord;
    }

}
