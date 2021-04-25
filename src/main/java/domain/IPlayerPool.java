/**
 * @Author: Aimé
 * @Date:   2021-04-13 03:01:48
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 12:07:08
 */
package domain;

import java.util.Map;

import domain.world.WorldCoordinate;

public interface IPlayerPool {
    IPlayer getPlayerById(String playerID);
    Account createNewPlayer(String playerName,String playerPassword);
    Map<String, IPlayer> getPlayers(); 
    Map<String, PlayerToken> getPlayerTokens();
    Account login(String username, String password);
    PlayerToken getNewPlayerToken();
    Account verifyCredentials(String playerID, String playerToken);
	WorldCoordinate claim(Account account, WorldCoordinate land,IWorldMap worldMap);
	IPlayer abandon(Account account, WorldCoordinate land,IWorldMap worldMap);
}