package app.domain.models;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class Invoice {
	private Long invoiceId;
	private Long petId;
	private Long ownersId;
	private Long orderId;
	private String productName;
	private double value;
	private int quantity;
	private Date invoiceDate;
	
	public Invoice(long invoiceId, Long petId, Long ownersId, Long orderId, String productName, double value,
			int quantity, Date invoiceDate) {
		this.invoiceId = invoiceId;
		this.petId = petId;
		this.ownersId = ownersId;
		this.orderId = orderId;
		this.productName = productName;
		this.value = value;
		this.quantity = quantity;
		this.invoiceDate = invoiceDate;
	}
	
	
	
	
	
	
}
