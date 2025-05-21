package app.adapters.order;

import app.adapters.order.entity.OrderEntity;
import app.adapters.order.repository.OrderRepository;
import app.domain.models.Order;
import app.ports.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(new OrderEntity(order));
    }

    @Override
    public void annulOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresent(orderEntity -> {
            orderEntity.setOrderCancelled(true);
            orderRepository.save(orderEntity);
        });
    }

    @Override
    public List<Order> findOrderbyPetId(Long petId) {
        return orderRepository.findAll().stream()
                .filter(orderEntity -> orderEntity.getPet() != null && orderEntity.getPet().getPetId().equals(petId))
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }
}
