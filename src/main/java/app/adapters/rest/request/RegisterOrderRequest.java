package app.adapters.rest.request;

import java.sql.Date;

import app.domain.models.Order;
import app.domain.models.Pet;
import app.domain.models.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterOrderRequest {
    private Long orderId;
    private Long petId;
    private Long veterinarianId;
    private String medication;
    private String drugDosage;
    private Date orderGeneration;
    private Boolean cancelled;

    public Order toOrder() {
        return Order.builder()
                .orderId(null)
                .pet(Pet.builder().petId(petId).build())
                .veterinarian(UserAccount.builder().document(veterinarianId).build())
                .medication(medication)
                .drugDosage(drugDosage)
                .orderGeneration(orderGeneration)
                .isOrderCancelled(false)
                .build();
    }

    public boolean isOrderCancelled() {
    return Boolean.TRUE.equals(this.isOrderCancelled());
}
}
