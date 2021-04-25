/**
 * @Author: Aimé
 * @Date:   2021-04-13 03:07:12
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-13 03:09:28
 */
package domain;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

// @Component
// @RestController
// @RequestMapping("sse")
// @Provider
@Path("sse")
@Singleton
public class SseResource implements ISseResource {
    public SseResource() { 
    }

    private Sse sse;
    private SseBroadcaster lightStateSseBroadcaster;
    private int evenId = 1;
    private int debugInitCallCounter = 0;
    private  OutboundSseEvent outboundSseEvent;

    @Context
    public void setInit(Sse sse) {
        this.sse = sse;
        this.lightStateSseBroadcaster = sse.newBroadcaster();
        debugInitCallCounter++;
        System.out.println("debugInitCallCounter: " + debugInitCallCounter);
    }

    @GET
    @Path("subscribe")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void subscribeLightEvents(@Context SseEventSink sseEventSink) {

        this.lightStateSseBroadcaster.register(sseEventSink);
        if(outboundSseEvent!=null)
        sseEventSink.send(outboundSseEvent); 
    }

    public void broadcastLightEvent(Map<String, IPlayer> players, String eventName) {
        evenId++;
       outboundSseEvent = sse.newEventBuilder().id(Integer.toString(evenId)).name(eventName)
                .mediaType(MediaType.APPLICATION_JSON_TYPE).data(Map.class, players).reconnectDelay(3000)
                .comment("light state change").build();
        lightStateSseBroadcaster.broadcast(outboundSseEvent);
    }

    
    int uptimeCounter=1; 
    private void updateCounter(){
        System.out.println("uptime: " + ++uptimeCounter);
    }
}