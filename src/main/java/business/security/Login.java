package business.security;

import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import domain.Account;
import domain.BGameSettings;
import domain.Util;
import domain.players.AccountManager;
import domain.players.NewAccountHelper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response.Status;
@Path("login")
public class Login {
    private final String SECRET_KEY=BGameSettings.getJWTKey();
    private final int SECRET_KEY_TTL=BGameSettings.getJWTKeyTTL();
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TYPE = "Bearer";

    @POST   
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAccount(NewAccountHelper account)
    {
        AccountManager.
 
    }
    @GET   
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticate(@Context HttpHeaders headers )
    {
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_HEADER); 
        if(!authorization.isEmpty())
        {
            String encodedUsernamePassword=getEncodedUserPassword(authorization.get(0));
            try { 
                String decodedUsernamePassword= new String(Base64.getDecoder().decode(encodedUsernamePassword));
                final StringTokenizer tokenizer = new StringTokenizer(decodedUsernamePassword, ":");
                final String username = tokenizer.nextToken();
                final String password = tokenizer.nextToken();

            } catch (IllegalArgumentException e) {
               Util.throwBadRequest("Invalid Base64 Scheme");
            } 
        } 
        return null; 
    } 
    private String getEncodedUserPassword(String authHeader) {
        if(authHeader!=null && !authHeader.isBlank()){
            String[] splitAuthHeader = authHeader.split(" "); 
            if (BEARER_TYPE.equals(splitAuthHeader[0]))  {
                return splitAuthHeader[0];
            } else {
                Util.throwBadRequest("Unsupported Authorization Scheme : " + splitAuthHeader[0]);
            } 
        }  
    Util.throwBadRequest(AUTHORIZATION_HEADER);
        return null;
    } 
}
