/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:12:26
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-07 22:01:24
 */
package domain; 

import java.util.Map;

public interface ICapital {
    Map<String,Object> getInfo(); 
    long getId();
    Map<String, Map<ResourceType, Integer>>  build(Building building);
 
}
