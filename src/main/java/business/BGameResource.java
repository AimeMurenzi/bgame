/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:13:24
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-12 04:03:56
 */
package business;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import domain.ResourceType;
import domain.buildings.BuildMaterialProducer;
import domain.buildings.ICapital;
import domain.buildings.Storage;

@Stateless
@Path("b")
public class BGameResource {
    @Inject 
    private ICapital capital;

    @GET
    @Path("login6")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@Context HttpHeaders headers) {
        System.out.println(headers.getRequestHeaders().toString());
        return headers.getRequestHeaders().toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> capitalInfo() { 
        return capital.getInfo();
    }
    @GET
    @Path("build/mine/mat1")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat1() { 
        BuildMaterialProducer BuildMaterialProducer=new BuildMaterialProducer(capital, ResourceType.BUILDMAT1);
        return capital.build(BuildMaterialProducer); 
    }
    @GET
    @Path("build/mine/mat2")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat2() { 
        BuildMaterialProducer BuildMaterialProducer=new BuildMaterialProducer(capital,ResourceType.BUILDMAT2);
        return capital.build(BuildMaterialProducer); 
    }
    @GET
    @Path("build/mine/mat3")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildMat3() { 
        BuildMaterialProducer BuildMaterialProducer=new BuildMaterialProducer(capital,ResourceType.BUILDMAT3);
        return capital.build(BuildMaterialProducer); 
    }
    @GET
    @Path("build/storage")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Map<ResourceType, Integer>> buildStorage() { 
    
        return capital.build(new Storage(capital)); 
    }
}
