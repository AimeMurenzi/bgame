/**
 * @Author: Aimé
 * @Date:   2021-06-13 13:30:10
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-10-12 21:18:03
 */
package business.resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

@Path("sse")
@Singleton
public class SseResource implements ISseResource {
    private Sse sse;
    private SseBroadcaster broadcaster;
    private int evenId = 1;
    private OutboundSseEvent outboundSseEvent;

    public SseResource() {
        // TODO: remove debug code
        // new Thread(() -> {
        // Integer counter = 0;
        // String eventName = "world-state";
        // while (true) {
        // if (sse != null && broadcaster != null) {
        // outboundSseEvent =
        // sse.newEventBuilder().id(Integer.toString(evenId)).name(eventName)
        // .mediaType(MediaType.APPLICATION_JSON_TYPE).data(Integer.class, ++counter)
        // .reconnectDelay(3000).comment(eventName).build();
        // broadcaster.broadcast(outboundSseEvent);
        // }

        // try {
        // Thread.sleep(3000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }

        // }).start();
    }

    @Context
    public void setInit(Sse sse) {
        this.sse = sse;
        this.broadcaster = sse.newBroadcaster();
    }

    @GET
    @Path("subscribe")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void subscribe(@Context SseEventSink sseEventSink) { 
        this.broadcaster.register(sseEventSink);
        if (outboundSseEvent != null) {
            sseEventSink.send(outboundSseEvent);

        }
    }

    public void broadcastEvent(List<LinkedHashMap<String, Object>> data, String eventName) {
        evenId++;
        // data(Map.class, data) is buggy and doesn't convert long properly; adds 3
        // zeroes at the end
        // no, its javascript that was the problem it cant handle long 
         
            outboundSseEvent = sse.newEventBuilder().id(Integer.toString(evenId)).name(eventName)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE).data(Map.class, data).reconnectDelay(3000)
                    .comment(eventName).build();
            broadcaster.broadcast(outboundSseEvent);
        

    }
}
