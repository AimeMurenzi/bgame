/**
 * @Author: Aimé
 * @Date:   2021-04-12 19:57:32
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 13:48:11
 */
package business;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Account;
import domain.IPlayer;
import domain.IPlayerPool;
import domain.ISseResource;
import domain.IWorldMap;
import domain.PlayerToken;
import domain.buildings.Building;
import domain.players.Player2;
import domain.world.IWorld;
import domain.world.WorldCoordinate;
import jakarta.ws.rs.PathParam;

@Stateless
// @Path("/")
public class WorldController extends Application {
    private final String userId = "userId";
    private final String userToken = "userToken";
    private final String userTokenExpireTimeSpan = "userTokenExpireTimeSpan";
    @Inject
    private IPlayerPool playerPool;
    @Inject
    private ISseResource sseResource;
    @Inject
    private IWorldMap worldMap;
    @Inject
    private IWorld world;

    @GET
    @Path("world")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorldCoordinate> worldMap() {
        return worldMap.getCoordinates();
    }

    @GET
    @Path("world/buildings")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Long, Building> buildings() {
        return world.getWorldBuildings();
    }

    @GET
    @Path("world/players")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Long, Player2> players2() {
        return world.getPlayers();
    }

    @GET
    @Path("world/claim/{xCoordinate},{yCoordinate}")
    @Produces(MediaType.APPLICATION_JSON)
    public WorldCoordinate claimLand(@Context HttpHeaders headers, @Context HttpServletResponse response,
            @PathParam("xCoordinate") int xCoordinate, @PathParam("yCoordinate") int yCoordinate) {

        Player2 player = authenticate(headers, response);
        WorldCoordinate land = new WorldCoordinate.Builder().x(xCoordinate).y(yCoordinate).build();
        land = worldMap.claimLand(player, land);
        if (land == null) {
            throw new BadRequestException();
        } 
        return land;
    }

    @POST
    @Path("newplayer")
    @Produces(MediaType.APPLICATION_JSON)
    public @Valid Player2 newPlayer(@Context HttpServletResponse response, @Valid Player2 account) {
        if (account == null) {
            throw new BadRequestException("No Account Data In Message Post Body");
        }
        if (world.add(account))
            return account;
        else
            return null;
        // Account newAccount = playerPool.createNewPlayer(account.getName(),
        // account.getPassword());
        // PlayerToken playerToken =
        // playerPool.getPlayerTokens().get(newAccount.getId());
        // response.setHeader(userId, newAccount.getId());
        // response.setHeader(userToken, playerToken.getPlayerToken());
        // response.setHeader(userTokenExpireTimeSpan,
        // Long.toString(playerToken.getLifeSpan()));
        // sseResource.broadcastLightEvent(players(), "players");
    }

    // @GET
    // @Path("claim")
    // @Produces(MediaType.APPLICATION_JSON)
    // public WorldCoordinate claim(@Context HttpHeaders headers, @Context HttpServletResponse response,
    //         WorldCoordinate worldCoordinate) {
    //     Account account = authenticate(headers, response);
    //     return playerPool.claim(account, worldCoordinate, worldMap);
    // }

    // @GET
    // @Path("abandon")
    // @Produces(MediaType.APPLICATION_JSON)
    // public IPlayer abandon(@Context HttpHeaders headers, @Context HttpServletResponse response,
    //         WorldCoordinate worldCoordinate) {
    //     Account account = authenticate(headers, response);
    //     return playerPool.abandon(account, worldCoordinate, worldMap);
    // }

    /**
     * 
     * @param headers
     * @param response
     * @return
     * @throws NotAuthorizedException
     */
    private Player2 authenticate(HttpHeaders headers, HttpServletResponse response) throws NotAuthorizedException {
        String playerID = headers.getHeaderString(userId);
        String playerToken = headers.getHeaderString(userToken);

        if (playerID != null && playerToken != null && !playerID.isBlank() && !playerToken.isBlank()) {
            Account account = playerPool.verifyCredentials(playerID, playerToken);
            if (account != null) {
                refreshPlayerToken(response, account);
                // return account;
            }
            throw new NotAuthorizedException("userid/usertoken");
        } else {
            throw new NotAuthorizedException("userid/usertoken");
        }
    }

    @GET
    @Path("players")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, IPlayer> players() {
        return playerPool.getPlayers();
    }

    private void badCredentialsWebAppException() {
        throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                .header("Content-Type", "application/json").entity("{\"message\":\"bad credentials\"}").build());
    }

    // @GET
    // @Path("login")
    // @Produces(MediaType.APPLICATION_JSON)
    // public void login(@HeaderParam("username") @DefaultValue("") String username,
    //         @HeaderParam("password") @DefaultValue("") String password, @Context HttpServletResponse response) {
    //     if (username.isBlank() || password.isBlank()) {
    //         badCredentialsWebAppException();
    //     }
    //     Account account = playerPool.login(username, password);
    //     if (account == null) {
    //         badCredentialsWebAppException();
    //     }
    //     refreshPlayerToken(response, account);
    // }

    private void refreshPlayerToken(HttpServletResponse response, Account account) {
        PlayerToken newPlayerToken = playerPool.getNewPlayerToken();
        playerPool.getPlayerTokens().put(account.getId(), newPlayerToken);
        response.setHeader(userId, account.getId());
        response.setHeader(userToken, newPlayerToken.getPlayerToken());
        response.setHeader(userTokenExpireTimeSpan, Long.toString(newPlayerToken.getLifeSpan()));
    }

    private String getHeader(Map<String, String> headers, String headerName) {
        return headers.get(headerName);
    }

    /**
     * expected milliseconds
     * 
     * @param duration
     * @return
     */
    private String formatTime(double duration) {
        duration = duration / 1000;
        if (duration > 0) {
            int intDuration = (int) duration;
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d", durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d", durationMinutes, durationSeconds);
            }
        }
        return "00:00:00";

    }
}