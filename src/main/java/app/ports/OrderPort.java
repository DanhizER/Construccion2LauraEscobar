package app.ports;

import java.util.List;
import app.domain.models.Order;

public interface OrderPort {
    void saveOrder(Order order);
    void annulOrder(Long orderId);
    List<Order> findOrderbyPetId(Long petId);
    Order findByOrderId(Long orderId);
    List<Order> findAllOrders();
}