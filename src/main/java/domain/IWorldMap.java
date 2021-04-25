/**
 * @Author: Aimé
 * @Date:   2021-04-11 21:42:48
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:07:18
 */
package domain;

import java.util.List;

import domain.players.Player2;
import domain.world.WorldCoordinate;

public interface IWorldMap {
	List<WorldCoordinate> getCoordinates();

	/**
	 * return a copy of the real WorldCoordinate, anything you do on this copy has
	 * no effect on the real thing
	 * 
	 * @param land
	 * @return
	 */
	WorldCoordinate get(final WorldCoordinate land);
	/**
	 * 
	 * @param player
	 * @param land
	 * @return
	 */
	WorldCoordinate claimLand(final Player2 player,final WorldCoordinate land);
//TODO: remove, replaced by claimland
	boolean setOwner(final IPlayer player,  final WorldCoordinate coordinate);

	/**
	 * -check if player owns coordinate<br>
	 * -if player owns coordinate set coordinate.player == null
	 * 
	 * @param player
	 * @param coordinate
	 * @return
	 */
	boolean removeOwner(final IPlayer player, final WorldCoordinate coordinate);
}