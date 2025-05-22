package app.adapters.invoice.entity;

import java.sql.Date;

import app.adapters.order.entity.OrderEntity;
import app.adapters.owner.entity.OwnerEntity;
import app.adapters.pet.entity.PetEntity;
import app.domain.models.Invoice;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "petid")
    private PetEntity pet;
    @ManyToOne
    @JoinColumn(name = "owners_id", referencedColumnName = "document")
    private OwnerEntity owner;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;
    @Column(name = "product_name")
	private String productName;
    @Column(name = "total")
	private double value;
    @Column(name = "quantity")
	private int quantity;
    @Column(name = "invoice_date")
	private Date invoiceDate;

    public InvoiceEntity(Invoice invoice) {
        this.invoiceId = invoice.getInvoiceId();
        this.pet = PetEntity.builder().petId(invoice.getPetId()).build();
        this.owner = OwnerEntity.builder().document(invoice.getOwnersId()).build();
        this.order = OrderEntity.builder().orderId(invoice.getOrderId()).build();
        this.productName = invoice.getProductName();
        this.value = invoice.getValue();
        this.quantity = invoice.getQuantity();
        this.invoiceDate = invoice.getInvoiceDate();
    }

    public Invoice toDomain() {
        return Invoice.builder()
                .invoiceId(this.invoiceId)
                .petId(this.pet != null ? this.pet.getPetId() : null)
                .ownersId(this.owner != null ? this.owner.getDocument() : null)
                .orderId(this.order != null ? this.order.getOrderId() : null)
                .productName(this.productName)
                .value(this.value)
                .quantity(this.quantity)
                .invoiceDate(this.invoiceDate)
                .build();
    }
    
}