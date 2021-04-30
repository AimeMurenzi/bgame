/**
 * @Author: Aimé
 * @Date:   2021-04-23 12:37:08
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:44:52
 */

package business.security;

import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import domain.Util;
import domain.players.AccountManager;
import domain.players.NewAccountHelper; 
 
@Path("users")
@Stateless
public class Users {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TYPE = "Basic";

    @Path("create/account")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAccount(@Valid NewAccountHelper account) {
        AccountManager.createAccount(account.getName(), account.getPassword());
    }

    @Path("login")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticate(@Context HttpHeaders headers) {
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_HEADER);
        if (!authorization.isEmpty()) {
            String encodedUsernamePassword = getEncodedUserPassword(authorization.get(0));
            try {
                String decodedUsernamePassword = new String(Base64.getDecoder().decode(encodedUsernamePassword));
                String decodedUsernamePasswordSplit[] = decodedUsernamePassword.split(":");
                if (decodedUsernamePasswordSplit.length == 2) {
                    final String username = decodedUsernamePasswordSplit[0];
                    final String password = decodedUsernamePasswordSplit[1];
                    return AccountManager.login(username, password);
                } 
                Util.throwUnAuthorized("Authorization: username | password");
            } catch (IllegalArgumentException e) {
                Util.throwBadRequest("Invalid Base64 Scheme");
            }
        }
        return null;
    }

    private String getEncodedUserPassword(String authHeader) {
        if (authHeader != null && !authHeader.isBlank()) {
            String[] splitAuthHeader = authHeader.split(" ");
            if (BEARER_TYPE.equals(splitAuthHeader[0])) {
                return splitAuthHeader[1];
            } else {
                Util.throwBadRequest("Unsupported Login Authorization Scheme : " + splitAuthHeader[0]);
            }
        }
        Util.throwBadRequest(AUTHORIZATION_HEADER);
        return null;
    }
}
