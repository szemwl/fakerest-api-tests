package commerce.store;

public interface ProcessedEventStore {

    boolean markEventProcessed(String eventId);

    boolean isEventProcessed(String eventId);

    int processedEventsCount();
}
