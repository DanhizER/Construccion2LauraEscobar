package app.adapters.order;

import app.adapters.order.entity.OrderEntity;
import app.adapters.order.repository.OrderRepository;
import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;
import app.adapters.useraccount.entity.UserAccountEntity;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.domain.models.Order;
import app.ports.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

@Component
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void saveOrder(Order order) {
        PetEntity pet = petRepository.findById(order.getPet().getPetId())
            .orElseThrow(() -> new RuntimeException("La mascota no existe en la base de datos"));
        OwnerEntity owner = pet.getOwnersId();
        if (owner == null) {
            throw new RuntimeException("El owner no existe en la base de datos");
        }
        // Busca el veterinario persistido
        UserAccountEntity vet = userAccountRepository.findByDocument(order.getVeterinarian().getDocument());
        if (vet == null) {
            throw new RuntimeException("El veterinario no existe en la base de datos");
        }
        // Crea la entidad de la orden y asigna el owner persistido
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOwnersId(owner);
        orderEntity.setPet(pet); 
        orderEntity.setVeterinarian(vet); 
        orderEntity.setMedication(order.getMedication());
        orderEntity.setDrugDosage(order.getDrugDosage());
        orderEntity.setOrderGeneration(order.getOrderGeneration());
        orderEntity.setOrderCancelled(order.isOrderCancelled());
        orderRepository.save(orderEntity);
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
