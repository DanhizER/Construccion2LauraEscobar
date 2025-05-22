package app.adapters.rest.response;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponse {
    private Long invoiceId;
	private Long petId;
	private Long ownersId;
	private Long orderId;
	private String productName;
	private Double value;
	private Integer quantity;
	private Date invoiceDate;

    public static InvoiceResponse fromInvoice(InvoiceResponse invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setInvoiceId(invoice.getInvoiceId());
        response.setPetId(invoice.getPetId());
        response.setOwnersId(invoice.getOwnersId());
        response.setOrderId(invoice.getOrderId());
        response.setProductName(invoice.getProductName());
        response.setValue(invoice.getValue());
        response.setQuantity(invoice.getQuantity());
        response.setInvoiceDate(invoice.getInvoiceDate());
        return response;
    }
}
