/**
 * @Author: Aimé
 * @Date:   2021-04-12 19:57:32
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:45:54
 */
package business.resources;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
    @Inject
    private ISseResource sseResource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LinkedHashMap<String, Object>> worldMap() {
        List<LinkedHashMap<String, Object>> worldCoordinates = getWorldState(); 
        return worldCoordinates;
    }

    private List<LinkedHashMap<String, Object>> getWorldState() {
        List<LinkedHashMap<String, Object>> worldCoordinates = new LinkedList<>();
        for (WorldCoordinate coordinate : WorldMap.getCoordinates()) {
            worldCoordinates.add(getRedactedCoordinate(coordinate)); 
        }
        return worldCoordinates;
    }

    private LinkedHashMap<String, Object> getRedactedCoordinate(WorldCoordinate coordinate) {
        return new LinkedHashMap<String, Object>() {
            {
                put("x", coordinate.getX());
                put("y", coordinate.getY());
                put("coordinateType", coordinate.getCoordinateType());
                put("free",coordinate.isFree());
                if (coordinate.getCapital() != null) {
                    put("capital", new LinkedHashMap<String, Object>() {
                        {
                            put("id", coordinate.getCapital().getId());
                            put("capitalName", coordinate.getCapital().getCapitalName());
                            put("level", coordinate.getCapital().getLevel());
                        }
                    });
                }
            }
        };
    }

    @GET
    @Path("players")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Player> players() {
        return World.getPlayers().values();
    }

    @GET
    @Path("claim/{xCoordinate}/{yCoordinate}")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedHashMap<String, Object> claimLand(@PathParam("xCoordinate") int xCoordinate, @PathParam("yCoordinate") int yCoordinate) {
        System.err.println(xCoordinate);
        Player player = World.getPlayers()
                .get(new Player.Builder().name(decodedJWT.getClaim("name").asString()).build());
                WorldCoordinate coordinate= WorldMap.getCoordinatesMap().get(new WorldCoordinate.Builder().x(xCoordinate).y(yCoordinate).build());
        if (player != null && coordinate!=null) {
            WorldMap.claimLand(player, coordinate);
            coordinate=WorldMap.getCoordinatesMap().get(coordinate);
            sseResource.broadcastEvent(getWorldState(), "world-state");
            System.out.println(coordinate.getCapital().getCapitalName());
        return getRedactedCoordinate(coordinate); 
        }
        return null;
       
    } 
    
}