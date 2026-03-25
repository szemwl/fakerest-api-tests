package commerce.store;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProcessedEventStore implements ProcessedEventStore {

    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    @Override
    public boolean markEventProcessed(String eventId) {
        return processedEventIds.add(eventId);
    }

    @Override
    public boolean isEventProcessed(String eventId) {
        return processedEventIds.contains(eventId);
    }

    @Override
    public int processedEventsCount() {
        return processedEventIds.size();
    }
}
