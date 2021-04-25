/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:12:26
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 04:10:05
 */
package domain.buildings; 

import java.util.Map;

import domain.ResourceType;

public interface ICapital {
    Map<String,Object> getInfo(); 
    long getId();
    Map<String, Map<ResourceType, Integer>>  build(Building building);
 
}
