package app.adapters.order.entity;

import java.sql.Date;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.pet.entity.PetEntity;
import app.adapters.useraccount.entity.UserAccountEntity;
import app.domain.models.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "petid")
	private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "owners_id", referencedColumnName = "document")
	private OwnerEntity ownersId;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id", referencedColumnName = "document")    
	private UserAccountEntity veterinarian;
    @Column(name = "medication")
	private String medication;
    @Column(name = "drug_dosage")
	private String drugDosage;
    @Column(name = "order_date")
	private Date orderGeneration;
    @Column(name = "order_status")
	private boolean isOrderCancelled;

    public OrderEntity(Order order) {
        this.orderId = order.getOrderId();
        this.pet = PetEntity.builder().petId(order.getPet() != null ? order.getPet().getPetId() : null).build();
        this.ownersId = OwnerEntity.builder().document(order.getOwnersId() != null ? order.getOwnersId().getDocument() : null).build();
        this.veterinarian = UserAccountEntity.builder().document(order.getVeterinarian() != null ? order.getVeterinarian().getDocument() : null).build();
        this.medication = order.getMedication();
        this.drugDosage = order.getDrugDosage();
        this.orderGeneration = order.getOrderGeneration();
        this.isOrderCancelled = order.isOrderCancelled();
    }

    public Order toDomain() {
        return Order.builder()
                .orderId(this.orderId)
                .pet(this.pet != null ? this.pet.toDomain() : null)
                .ownersId(this.ownersId != null ? this.ownersId.toDomain() : null)
                .veterinarian(this.veterinarian != null ? this.veterinarian.toDomain() : null)
                .medication(this.medication)
                .drugDosage(this.drugDosage)
                .orderGeneration(this.orderGeneration)
                .isOrderCancelled(this.isOrderCancelled)
                .build();
    }
}
