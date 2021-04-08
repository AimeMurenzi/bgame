/**
 * @Author: Aimé
 * @Date:   2021-04-07 04:13:24
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-08 07:30:14
 */
package business;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.BuildMaterialProducer;
import domain.ICapital;
import domain.ResourceType;
import domain.Storage;

@Stateless
@Path("")
public class BGameResource {
    @Inject 
    private ICapital capital;

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
