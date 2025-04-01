package app.domain.models;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Invoice {
	private long invoiceId;
	private long petId;
	private long ownersId;
	private long orderId;
	private String productName;
	private double value;
	private int quantity;
	private Date invoiceDate;
	
	public Invoice(long invoiceId, long petId, long ownersId, long idOrder, String productName, double value,
			int quantity, Date invoiceDate) {
		this.invoiceId = invoiceId;
		this.petId = petId;
		this.ownersId = ownersId;
		this.orderId = idOrder;
		this.productName = productName;
		this.value = value;
		this.quantity = quantity;
		this.invoiceDate = invoiceDate;
	}
	
	
}
