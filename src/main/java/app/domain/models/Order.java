package app.domain.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	private long orderId;
	private Pet pet;
	private Person ownersId;
	private UserAccount veterinarian;
	private String medication;
	private String drugDosage;
	private Date orderGeneration;
	private boolean isOrderCancelled;
	
	public Order(long orderId, Pet pet, Person ownersId, UserAccount veterinarian, String medication, String drugDosage,
			Date orderGeneration) {
		this.orderId = orderId;
		this.pet = pet;
		this.ownersId = ownersId;
		this.veterinarian = veterinarian;
		this.medication = medication;
		this.drugDosage = drugDosage;
		this.orderGeneration = orderGeneration;
	}
	

}
