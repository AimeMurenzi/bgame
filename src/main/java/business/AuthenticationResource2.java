package business;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import domain.BGameSettings; 

@Stateless
@Path("")
public class AuthenticationResource2 {
    private final String SECRET_KEY=BGameSettings.getJWTKey();
    private final int SECRET_KEY_TTL=BGameSettings.getJWTKeyTTL();
    @GET
    @Path("/refresh/token")
    @Produces(MediaType.TEXT_PLAIN)
    public String refreshToken(@Context HttpHeaders headers) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String userAgent=headers.getHeaderString("User-Agent");
            String token = JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+60000))
                .sign(algorithm);
                return token+ "\n\r" +userAgent ;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } 
        System.out.println(headers.getRequestHeaders().toString());
        return "clogin "+headers.getRequestHeaders().toString();
    }
    

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@Context HttpHeaders headers) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String userAgent=headers.getHeaderString("User-Agent");
            String token = JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+60000))
                .sign(algorithm);
                return token+ "\n\r" +userAgent ;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } 
        System.out.println(headers.getRequestHeaders().toString());
        return "clogin "+headers.getRequestHeaders().toString();
    }

}
