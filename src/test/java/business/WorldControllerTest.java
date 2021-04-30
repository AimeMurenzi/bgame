/**
 * @Author: Aimé
 * @Date:   2021-04-13 13:50:07
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 13:50:08
 */
package business;

 

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import business.resources.WorldController;
import jakarta.ws.rs.core.Application;

public class WorldControllerTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(WorldController.class);
    }
}
