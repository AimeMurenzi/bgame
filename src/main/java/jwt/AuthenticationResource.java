package jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestScoped
@Path("/jwt")
@JwtAuthz
public class AuthenticationResource {

    @Inject
    private DecodedJWT decodedJWT;

    @GET
    @Path("/authenticate")
    public Response authenticate() {
        Map<String, Claim> claims = decodedJWT.getClaims();
        
        JsonObject response = Json.createObjectBuilder()
                .add("name", claims.getOrDefault("name", new DefaultClaim("no name")).asString())
                .add("subject", claims.getOrDefault("name", new DefaultClaim("no subject")).asString())
                .build();

        return Response.ok(response).build();
    }
    class DefaultClaim implements Claim{
        private String asStringMock="";
        public DefaultClaim(String defaultMock){
            this.asStringMock=defaultMock;
        }

        @Override
        public boolean isNull() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public Boolean asBoolean() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Integer asInt() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Long asLong() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Double asDouble() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String asString() { 
            return this.asStringMock;
        }

        @Override
        public Date asDate() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T[] asArray(Class<T> tClazz) throws JWTDecodeException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> List<T> asList(Class<T> tClazz) throws JWTDecodeException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Map<String, Object> asMap() throws JWTDecodeException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T as(Class<T> tClazz) throws JWTDecodeException {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
