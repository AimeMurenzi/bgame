/**
 * @Author: Aimé
 * @Date:   2021-04-13 03:03:51
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 03:06:10
 */
package domain;
import java.util.Map;

public interface ISseResource {
    void broadcastLightEvent(Map<String, IPlayer> players, String eventName);
}