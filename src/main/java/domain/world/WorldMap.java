/**
 * @Author: Aimé
 * @Date:   2021-04-29 00:35:02
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:52:13
 */
package domain.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.buildings.Capital;
import domain.players.Player;

public class WorldMap {
    private static class HoldInstance {
        private static final WorldMap INSTANCE = new WorldMap();
    }

    public static WorldMap getInstance() { 
        return HoldInstance.INSTANCE;
    }

    /* #region Default World */
    private String world1 = "0,0,0\n" + "5,0,0\n" + "8,0,0\n" + "10,0,0\n" + "15,0,0\n" + "19,0,0\n" + "3,1,0\n"
            + "7,1,0\n" + "15,1,0\n" + "3,2,0\n" + "7,2,0\n" + "4,3,0\n" + "17,4,0\n" + "18,4,0\n" + "0,5,0\n"
            + "5,5,0\n" + "7,5,0\n" + "9,5,0\n" + "10,5,0\n" + "12,5,0\n" + "15,5,0\n" + "17,5,0\n" + "9,6,0\n"
            + "10,7,0\n" + "12,7,0\n" + "4,9,0\n" + "0,10,0\n" + "1,10,0\n" + "5,10,0\n" + "6,10,0\n" + "10,10,0\n"
            + "15,10,0\n" + "8,11,0\n" + "14,11,0\n" + "2,12,0\n" + "19,12,0\n" + "0,13,0\n" + "1,13,0\n" + "2,13,0\n"
            + "2,14,0\n" + "9,14,0\n" + "0,15,0\n" + "1,15,0\n" + "5,15,0\n" + "10,15,0\n" + "15,15,0\n" + "11,16,0\n"
            + "15,17,0\n" + "11,18,0\n" + "4,0,1\n" + "7,0,1\n" + "9,0,1\n" + "13,0,1\n" + "4,1,1\n" + "8,1,1\n"
            + "9,1,1\n" + "13,1,1\n" + "17,1,1\n" + "18,1,1\n" + "19,1,1\n" + "4,2,1\n" + "13,2,1\n" + "14,2,1\n"
            + "15,2,1\n" + "2,3,1\n" + "3,3,1\n" + "5,3,1\n" + "13,3,1\n" + "18,3,1\n" + "1,4,1\n" + "14,4,1\n"
            + "16,4,1\n" + "19,4,1\n" + "1,5,1\n" + "4,5,1\n" + "6,5,1\n" + "11,5,1\n" + "14,5,1\n" + "19,5,1\n"
            + "0,6,1\n" + "2,6,1\n" + "6,6,1\n" + "7,6,1\n" + "12,6,1\n" + "13,6,1\n" + "0,7,1\n" + "11,7,1\n"
            + "13,7,1\n" + "14,7,1\n" + "15,7,1\n" + "0,8,1\n" + "5,8,1\n" + "12,8,1\n" + "14,8,1\n" + "0,9,1\n"
            + "1,9,1\n" + "2,9,1\n" + "3,9,1\n" + "5,9,1\n" + "12,9,1\n" + "13,9,1\n" + "14,9,1\n" + "15,9,1\n"
            + "2,10,1\n" + "7,10,1\n" + "14,10,1\n" + "1,11,1\n" + "2,11,1\n" + "10,11,1\n" + "1,12,1\n" + "8,12,1\n"
            + "10,12,1\n" + "13,12,1\n" + "14,12,1\n" + "8,13,1\n" + "13,13,1\n" + "13,14,1\n" + "2,15,1\n" + "6,15,1\n"
            + "7,15,1\n" + "11,15,1\n" + "14,15,1\n" + "1,16,1\n" + "2,16,1\n" + "3,16,1\n" + "4,16,1\n" + "5,16,1\n"
            + "14,16,1\n" + "2,17,1\n" + "3,17,1\n" + "4,17,1\n" + "5,17,1\n" + "7,17,1\n" + "12,17,1\n" + "13,17,1\n"
            + "0,18,1\n" + "1,18,1\n" + "10,18,1\n" + "12,18,1\n" + "13,18,1\n" + "0,19,1\n" + "3,19,1\n" + "9,19,1\n"
            + "10,19,1\n" + "14,19,1\n" + "15,19,1\n" + "14,0,2\n" + "14,1,2\n" + "16,2,2\n" + "17,2,2\n" + "18,2,2\n"
            + "14,3,2\n" + "15,3,2\n" + "17,3,2\n" + "2,4,2\n" + "15,4,2\n" + "2,5,2\n" + "3,5,2\n" + "1,6,2\n"
            + "3,6,2\n" + "4,6,2\n" + "5,6,2\n" + "10,6,2\n" + "11,6,2\n" + "2,7,2\n" + "4,7,2\n" + "6,7,2\n"
            + "7,7,2\n" + "4,8,2\n" + "7,8,2\n" + "13,8,2\n" + "6,9,2\n" + "7,9,2\n" + "11,10,2\n" + "12,10,2\n"
            + "13,10,2\n" + "11,11,2\n" + "13,11,2\n" + "19,11,2\n" + "9,12,2\n" + "11,12,2\n" + "12,12,2\n"
            + "10,13,2\n" + "11,13,2\n" + "12,13,2\n" + "10,14,2\n" + "11,14,2\n" + "12,14,2\n" + "14,14,2\n"
            + "12,15,2\n" + "13,15,2\n" + "6,16,2\n" + "7,16,2\n" + "12,16,2\n" + "13,16,2\n" + "14,17,2\n"
            + "14,18,2\n" + "19,2,3\n" + "16,3,3\n" + "19,3,3\n" + "3,4,3\n" + "1,7,3\n" + "3,7,3\n" + "5,7,3\n"
            + "1,8,3\n" + "2,8,3\n" + "3,8,3\n" + "6,8,3\n" + "12,11,3\n" + "9,13,3\n" + "6,17,3\n" + "6,0,4\n"
            + "11,0,4\n" + "12,1,4\n" + "1,2,4\n" + "2,2,4\n" + "6,2,4\n" + "9,2,4\n" + "10,2,4\n" + "12,2,4\n"
            + "0,3,4\n" + "7,3,4\n" + "9,3,4\n" + "5,4,4\n" + "9,4,4\n" + "12,4,4\n" + "15,6,4\n" + "16,6,4\n"
            + "17,6,4\n" + "16,7,4\n" + "19,7,4\n" + "8,8,4\n" + "10,8,4\n" + "18,8,4\n" + "8,9,4\n" + "10,9,4\n"
            + "16,9,4\n" + "17,9,4\n" + "3,10,4\n" + "9,10,4\n" + "18,10,4\n" + "0,11,4\n" + "4,11,4\n" + "6,11,4\n"
            + "15,11,4\n" + "5,12,4\n" + "6,12,4\n" + "7,12,4\n" + "6,13,4\n" + "6,14,4\n" + "18,14,4\n" + "8,15,4\n"
            + "10,16,4\n" + "10,17,4\n" + "16,17,4\n" + "5,18,4\n" + "16,18,4\n" + "1,19,4\n" + "2,19,4\n" + "5,19,4\n"
            + "7,19,4\n" + "11,19,4\n" + "19,19,4\n" + "1,0,5\n" + "2,0,5\n" + "3,0,5\n" + "12,0,5\n" + "16,0,5\n"
            + "17,0,5\n" + "18,0,5\n" + "0,1,5\n" + "1,1,5\n" + "2,1,5\n" + "5,1,5\n" + "6,1,5\n" + "10,1,5\n"
            + "11,1,5\n" + "16,1,5\n" + "0,2,5\n" + "5,2,5\n" + "8,2,5\n" + "11,2,5\n" + "1,3,5\n" + "6,3,5\n"
            + "8,3,5\n" + "10,3,5\n" + "11,3,5\n" + "12,3,5\n" + "0,4,5\n" + "4,4,5\n" + "6,4,5\n" + "7,4,5\n"
            + "8,4,5\n" + "10,4,5\n" + "11,4,5\n" + "13,4,5\n" + "8,5,5\n" + "13,5,5\n" + "16,5,5\n" + "18,5,5\n"
            + "8,6,5\n" + "14,6,5\n" + "18,6,5\n" + "19,6,5\n" + "8,7,5\n" + "9,7,5\n" + "17,7,5\n" + "18,7,5\n"
            + "9,8,5\n" + "11,8,5\n" + "15,8,5\n" + "16,8,5\n" + "17,8,5\n" + "19,8,5\n" + "9,9,5\n" + "11,9,5\n"
            + "18,9,5\n" + "19,9,5\n" + "4,10,5\n" + "8,10,5\n" + "16,10,5\n" + "17,10,5\n" + "19,10,5\n" + "3,11,5\n"
            + "5,11,5\n" + "7,11,5\n" + "9,11,5\n" + "16,11,5\n" + "17,11,5\n" + "18,11,5\n" + "0,12,5\n" + "3,12,5\n"
            + "4,12,5\n" + "15,12,5\n" + "16,12,5\n" + "17,12,5\n" + "18,12,5\n" + "3,13,5\n" + "4,13,5\n" + "5,13,5\n"
            + "7,13,5\n" + "14,13,5\n" + "15,13,5\n" + "16,13,5\n" + "17,13,5\n" + "18,13,5\n" + "19,13,5\n"
            + "0,14,5\n" + "1,14,5\n" + "3,14,5\n" + "4,14,5\n" + "5,14,5\n" + "7,14,5\n" + "8,14,5\n" + "15,14,5\n"
            + "16,14,5\n" + "17,14,5\n" + "19,14,5\n" + "3,15,5\n" + "4,15,5\n" + "9,15,5\n" + "16,15,5\n" + "17,15,5\n"
            + "18,15,5\n" + "19,15,5\n" + "0,16,5\n" + "8,16,5\n" + "9,16,5\n" + "15,16,5\n" + "16,16,5\n" + "17,16,5\n"
            + "18,16,5\n" + "19,16,5\n" + "0,17,5\n" + "1,17,5\n" + "8,17,5\n" + "9,17,5\n" + "11,17,5\n" + "17,17,5\n"
            + "18,17,5\n" + "19,17,5\n" + "2,18,5\n" + "3,18,5\n" + "4,18,5\n" + "6,18,5\n" + "7,18,5\n" + "8,18,5\n"
            + "9,18,5\n" + "15,18,5\n" + "17,18,5\n" + "18,18,5\n" + "19,18,5\n" + "4,19,5\n" + "6,19,5\n" + "8,19,5\n"
            + "12,19,5\n" + "13,19,5\n" + "16,19,5\n" + "17,19,5\n" + "18,19,5\n";
    /* #endregion */
    private WorldMap(){
        loadMap(world1, coordinates, coordinatesMap);
    } 
    private CoordinateType getTypeInStringForm(String type) {
        switch (type) {
        case "0":
            return CoordinateType.LAND;
        case "1":
            return CoordinateType.WATER;
        case "2":
            return CoordinateType.FOREST; 
        default:
            return CoordinateType.UNKNOWN;
        }
    }
    private final Map<WorldCoordinate, WorldCoordinate> coordinatesMap = new HashMap<>();
    private final List<WorldCoordinate> coordinates = new ArrayList<>();
    private List<WorldCoordinate> loadMap(String mapName,List<WorldCoordinate> coordinates,Map<WorldCoordinate, WorldCoordinate> coordinatesMap) {
        coordinates.clear();
        coordinatesMap.clear();
        String[] worldCoordinateSplit = world1.split("\n");
        for (String string : worldCoordinateSplit) {
            String[] line = string.split(",");
            if (line.length == 3) {
                WorldCoordinate worldCoordinate =
                new WorldCoordinate.Builder()
                .x(Integer.parseInt(line[0]))
                .y(Integer.parseInt(line[1]))
                .coordinateType(getTypeInStringForm(line[2]))
                .build(); 
                coordinates.add(worldCoordinate);
                coordinatesMap.put(worldCoordinate, worldCoordinate);
            }

        }
        Collections.sort(coordinates, new CoordinateSortingRule()); 
        return coordinates;
    }
    public static List<WorldCoordinate> getCoordinates() {
        return new ArrayList<>(getInstance().coordinates);
    }
    public static void claimLand(Player player, WorldCoordinate land) { 
        // when capital is null then coordinate is free
        // this defaultCoordinate is for case where land to claim does not exist
        // prevents passing the isFree check point
       WorldCoordinate defaultCoordinate=new WorldCoordinate.Builder()
       .capital(new Capital())
       .build();
       WorldCoordinate worldCoordinate =getInstance().coordinatesMap.getOrDefault(land,defaultCoordinate);
       if (worldCoordinate.isFree()) {
           Capital newCapital=new Capital(player.getId(),String.format("Capital[%d,%d]", worldCoordinate.getX(),worldCoordinate.getY())); 
           worldCoordinate.setCapital(newCapital);
           World.addBuilding(newCapital);   
       }  
    }
    public static Map<WorldCoordinate, WorldCoordinate> getCoordinatesMap() {
        return new HashMap<>(getInstance().coordinatesMap) ;
    }
    public boolean abandonLand(WorldCoordinate coordinate) {
       
        return false;
    }
}
