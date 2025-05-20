package app.adapters.invoice.entity;

import java.sql.Date;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.pet.entity.PetEntity;
import app.domain.models.Invoice;
import app.domain.models.Pet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "owners_id", referencedColumnName = "owners_id")
    private OwnerEntity owner;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;
    @Column(name = "product_name")
	private String productName;
    @Column(name = "total")
	private double value;
    @Column(name = "description")
	private int quantity;
    @Column(name = "invoice_date")
	private Date invoiceDate;

    public InvoiceEntity(Long invoiceId, PetEntity pet, OwnerEntity owner, OrderEntity order, String productName,
            double value, int quantity, Date invoiceDate) {
        this.invoiceId = invoiceId;
        this.pet = pet;
        this.owner = owner;
        this.order = order;
        this.productName = productName;
        this.value = value;
        this.quantity = quantity;
        this.invoiceDate = invoiceDate;
    }

    public Invoice toDomain() {
        return Invoice.builder()
                .invoiceId = (this.invoiceId)
                .pet = (this.pet)
                .owner = (this.owner)
                .order = (this.order)
                .productName = (this.productName)
                .value = (this.value)
                .quantity = (this.quantity)
                .invoiceDate = (this.invoiceDate)
                .build();
    }
    
}