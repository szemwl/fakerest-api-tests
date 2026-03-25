package commerce.service;

import commerce.event.PaymentEvent;
import commerce.model.OrderStatus;
import commerce.store.OrderStore;
import commerce.store.ProcessedEventStore;

public class PaymentEventHandler {

    private final OrderStore orderStore;
    private final ProcessedEventStore processedEventStore;

    public PaymentEventHandler(OrderStore orderStore, ProcessedEventStore processedEventStore) {
        this.orderStore = orderStore;
        this.processedEventStore = processedEventStore;
    }

    public void handle(PaymentEvent event) {
        if (!processedEventStore.markEventProcessed(event.eventId())) {
            return;
        }

        if (event.success()) {
            orderStore.updateStatus(event.orderId(), OrderStatus.PAID);
        }
    }
}
