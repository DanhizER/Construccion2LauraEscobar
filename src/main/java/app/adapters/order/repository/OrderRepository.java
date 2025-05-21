package app.adapters.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByPet_PetId(Long petId);
}
