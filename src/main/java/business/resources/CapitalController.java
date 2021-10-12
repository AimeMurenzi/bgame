/**
 * @Author: Aimé
 * @Date:   2021-06-12 11:30:33
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-07-21 22:12:31
 */
package business.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.auth0.jwt.interfaces.DecodedJWT;

import business.security.JWTBind;
import domain.Util;
import domain.buildings.Building;
import domain.buildings.Capital;
import domain.players.Player;
import domain.world.World; 

@Stateless
@JWTBind
@Path("capitals")
public class CapitalController {
    @Inject
    private DecodedJWT decodedJWT;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getAllPlayerCapitals() {
        Player player = getPlayerByName();
        List<Map<String, Object>> capitals = new ArrayList<>();
        for (Building building : World.getWorldBuildings().values()) {
            if (building instanceof Capital) {
                Capital capital = (Capital) building;
                if (capital.getParentId() == player.getId()) {
                    capitals.add(capital.getInfo());
                }
            }
        }
        return capitals;
    }

    private Player getPlayerByName() {
        return World.getPlayers().get(new Player.Builder().name(decodedJWT.getClaim("name").asString()).build());
    }

    @GET
    @Path("{capitalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getCapitalInformation(@PathParam("capitalId") String capitalId) { 
        Map<String, Building> worldBuildings=new HashMap<>(World.getWorldBuildings());
        for (Building building : worldBuildings.values()) {
            System.out.println("capital"+building.getId());
        }
        Building building = worldBuildings.get( capitalId);
        if (building != null) {
            if (building instanceof Capital) {
                Capital capital = (Capital) building;
                Player player = getPlayerByName();
                return capital.getInfo(player);
            }
        }
         Util.throwBadRequest("capital  does not exit");
        return null;

    }

}
