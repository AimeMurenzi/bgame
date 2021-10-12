package business.resources;

import java.util.LinkedHashMap;
import java.util.List;

public interface ISseResource {
    void broadcastEvent(List<LinkedHashMap<String, Object>> data, String eventName);
}
