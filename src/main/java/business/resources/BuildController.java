/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:13:24
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:27:50
 */
package business.resources;

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
import domain.ResourceType;
import domain.Util;
import domain.buildings.BuildMaterialProducer;
import domain.buildings.Building;
import domain.buildings.Capital;
import domain.buildings.Storage;
import domain.players.Player;
import domain.world.World; 
@Stateless
@JWTBind
@Path("build")
public class BuildController {
    @Inject
    private DecodedJWT decodedJWT;

    @GET
    @Path("mine/mat1/in/{capital}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat1(@PathParam("capital") long capitalId) {
        return buildBuildMaterialProducer(capitalId, ResourceType.BUILDMAT1);
    }

    @GET
    @Path("mine/mat2/in/{capital}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat2(@PathParam("capital") long capitalId) {
        return buildBuildMaterialProducer(capitalId, ResourceType.BUILDMAT2);
    }

    @GET
    @Path("mine/mat3/in/{capital}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat3(@PathParam("capital") long capitalId) {
        return buildBuildMaterialProducer(capitalId, ResourceType.BUILDMAT3);
    }

    @GET
    @Path("storage/in/{capital}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildStorage(@PathParam("capital") long capitalId) {
        return build(capitalId, new Storage(capitalId));
    }

    private Map<String, Map<ResourceType, Integer>> buildBuildMaterialProducer(long capitalId,
            ResourceType resourceType) {
        BuildMaterialProducer buildMaterialProducer = new BuildMaterialProducer(capitalId, resourceType);
        return build(capitalId, buildMaterialProducer);
    }

    private Map<String, Map<ResourceType, Integer>> build(long capitalId, Building building) {
        Player player = World.getPlayers()
                .get(new Player.Builder().name(decodedJWT.getClaim("name").asString()).build());
        if (player != null) {
            final Map<Long, Building> worldBuildings = World.getWorldBuildings();
            Building playerCapital = worldBuildings.get(capitalId);
            if (playerCapital != null && playerCapital instanceof Capital
                    && playerCapital.getParentId() == player.getId()) {
                Capital capital = (Capital) playerCapital;
                return capital.build(building);// returns missing resource if there are any
            }
            Util.throwBadRequest("Capital Does Not Belong To Player");
            // get player capital
            // check if player has path param capital
            // build building in capital
        }
        Util.throwBadRequest("Player Does Not Exist");
        return null;
    }
}
