package commerce.store;

import commerce.model.Order;
import commerce.model.OrderStatus;

import java.util.Optional;

public interface OrderStore {

    void save(Order order);

    Optional<Order> findById(String orderId);

    void updateStatus(String orderId, OrderStatus newStatus);
}
