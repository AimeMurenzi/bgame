/**
 * @Author: Aimé
 * @Date:   2021-04-12 19:57:32
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:45:54
 */
package business.resources;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.auth0.jwt.interfaces.DecodedJWT;

import business.security.JWTBind;
import domain.players.Player;
import domain.world.World;
import domain.world.WorldCoordinate;
import domain.world.WorldMap;

@RequestScoped
@Path("world")
@JWTBind
public class WorldController extends Application {
    @Inject
    private DecodedJWT decodedJWT;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorldCoordinate> worldMap() {
        return WorldMap.getCoordinates();
    }

    @GET
    @Path("players")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Player> players() {
        return World.getPlayers().values();
    }

    @GET
    @Path("claim/{xCoordinate}/{yCoordinate}")
    public void claimLand(@PathParam("xCoordinate") int xCoordinate, @PathParam("yCoordinate") int yCoordinate) {
        Player player = World.getPlayers()
                .get(new Player.Builder().name(decodedJWT.getClaim("name").asString()).build());
        if (player != null) {
            WorldMap.claimLand(player, new WorldCoordinate.Builder().x(xCoordinate).y(yCoordinate).build());
        }
    }
}