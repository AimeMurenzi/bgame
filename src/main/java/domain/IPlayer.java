/**
 * @Author: Aimé
 * @Date:   2021-04-11 21:40:45
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:06:59
 */
package domain;

import java.util.List;

import domain.world.WorldCoordinate;

public interface IPlayer {
	boolean hasLand(WorldCoordinate land);
    List<WorldCoordinate> getClaimedLands();
    /**
     * 
     * @param coordinate
     * @return 
     */
    boolean claimLand(WorldCoordinate coordinate, IWorldMap worldMap); 
    boolean abandonLand(WorldCoordinate coordinate, IWorldMap worldMap);
}