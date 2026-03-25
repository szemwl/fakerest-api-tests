package commerce.event;

public record PaymentEvent(
        String eventId,
        String orderId,
        boolean success
) {
}
