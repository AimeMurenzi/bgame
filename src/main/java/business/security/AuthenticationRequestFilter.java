/**
 * @Author: Aimé
 * @Date:   2021-04-28 10:18:59
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:46:29
 */
package business.security;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import domain.BGameSettings;
import domain.Util;

@Singleton
@JWTBind
@Provider
public class AuthenticationRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        decodedJWThreadLocal.remove();

    }

    private ThreadLocal<DecodedJWT> decodedJWThreadLocal = new ThreadLocal<>();

    @Produces
    @RequestScoped
    public DecodedJWT decodedJWTInjection() {
        return decodedJWThreadLocal.get();
    }

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TYPE = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            String authorizationHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);
            if (authorizationHeader != null && !authorizationHeader.isBlank()) {
                String[] splitAuthorizationHeader = authorizationHeader.split(" ");
                if (splitAuthorizationHeader.length == 2 && BEARER_TYPE.equals(splitAuthorizationHeader[0])) {
                    String tokenJWT = splitAuthorizationHeader[1];
                    final String SECRET_KEY = BGameSettings.getJWTKey();
                    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                    Verification verification = JWT.require(algorithm);
                    JWTVerifier verifier = verification.build();
                    DecodedJWT decodedJWT = verifier.verify(tokenJWT);
                    this.decodedJWThreadLocal.set(decodedJWT);
                    return;

                }
                throw new SecurityException("Unsupported Authorization Scheme");
            }
            throw new SecurityException("Authorization Information Missing");
        } catch (JWTVerificationException jve) {
            Util.throwUnAuthorized("Invalid JWT token");
        } catch (SecurityException se) {
            Util.throwUnAuthorized(se.getMessage());
        }
    }

}
