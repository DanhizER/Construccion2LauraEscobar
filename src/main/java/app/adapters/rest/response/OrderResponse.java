package app.adapters.rest.response;

import java.sql.Date;

import app.domain.models.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Long petId;
    private Long veterinarianId;
    private String medication;
    private String drugDosage;
    private Date orderGeneration;
    private Boolean cancelled;

    public static OrderResponse fromOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setPetId(order.getPet().getPetId());
        response.setVeterinarianId(order.getVeterinarian().getDocument());
        response.setMedication(order.getMedication());
        response.setDrugDosage(order.getDrugDosage());
        response.setOrderGeneration(order.getOrderGeneration());
        return response;
    }
}
