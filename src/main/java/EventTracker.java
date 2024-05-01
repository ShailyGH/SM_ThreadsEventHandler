import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }
    public Map<String, Integer> tracker()
    {
        return tracker;
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        if(tracker.containsKey(message))
        {
            tracker.replace(message, tracker.get(message)+1);
        }
        else
        {
            tracker.put(message, 1);
        }
    }

    synchronized public Boolean has(String message) {
        return tracker.containsKey(message);
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        if(tracker.containsKey(message))
        {
            tracker.replace(message, tracker.get(message)-1);
            if (tracker.get(message) == 0) {
                tracker.remove(message);
            }
        }
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}
