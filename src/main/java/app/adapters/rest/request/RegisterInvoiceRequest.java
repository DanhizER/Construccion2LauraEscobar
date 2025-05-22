package app.adapters.rest.request;

import java.sql.Date;

import app.domain.models.Invoice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterInvoiceRequest {
    private Long invoiceId;
	private Long petId;
	private Long ownersId;
	private Long orderId;
	private String productName;
	private Double value;
	private Integer quantity;
	private Date invoiceDate;

    public Invoice toInvoice() {
        return Invoice.builder()
                .invoiceId(invoiceId)
                .petId(petId)
                .ownersId(ownersId)
                .orderId(orderId)
                .productName(productName)
                .value(value)
                .quantity(quantity)
                .invoiceDate(invoiceDate)
                .build();
    }
}
