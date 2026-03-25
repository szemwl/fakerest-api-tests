package commerce.model;

public record Order(
        String id,
        OrderStatus status
) {
}
