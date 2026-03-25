package commerce.event;

import commerce.model.OrderStatus;

public record OrderCreatedEvent(
        String orderId,
        OrderStatus status
) {
}
