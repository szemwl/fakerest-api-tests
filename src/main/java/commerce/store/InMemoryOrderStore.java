package commerce.store;

import commerce.model.Order;
import commerce.model.OrderStatus;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderStore implements OrderStore {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public void save(Order order) {
        orders.put(order.id(), order);
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public void updateStatus(String orderId, OrderStatus newStatus) {
        Order existingOrder = orders.get(orderId);

        if (existingOrder == null) {
            throw new IllegalArgumentException("Заказ не найден: " + orderId);
        }

        orders.put(orderId, new Order(existingOrder.id(), newStatus));
    }
}
