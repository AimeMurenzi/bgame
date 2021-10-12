/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:12:26
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 22:14:08
 */
package domain.buildings; 

import java.util.Map;

import domain.ResourceType;
import domain.players.Player;

public interface ICapital {
    Map<String,Object> getInfo();
    Map<String,Object> getInfo(Player player); 
    String getId();
    Map<String, Map<ResourceType, Integer>>  build(Building building);
 
}
