package app.adapters.rest.request;

import java.sql.Date;

import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.domain.models.Pet;
import app.domain.models.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterMedicalHistoryRequest {
    private Long medicalHistoryId;
    private Long petId;
    private Long veterinarianId;
    private String reasonConsultation;
    private String symptomatology;
    private Long orderId;
    private String diagnosis;
    private String procedure;
    private String medication;
    private String drugDosage;
    private String vaccinationHistory;
    private String allergyMedications;
    private String procedureDetail;
    private Date dateRegistration;
    private boolean orderCancellation;

    public MedicalHistory toMedicalHistory() {
        return MedicalHistory.builder()
                .medicalHistoryID(null)
                .pet(Pet.builder().petId(petId).build())
                .veterinarian(UserAccount.builder().document(veterinarianId).build())
                .order(orderId != null ? Order.builder().orderId(orderId).build() : null)
                .reasonConsultation(reasonConsultation)
                .symptomatology(symptomatology)
                .diagnosis(diagnosis)
                .procedure(procedure)
                .medication(medication)
                .drugDosage(drugDosage)
                .dateRegistration(dateRegistration)
                .orderCancellation(orderCancellation)
                .vaccinationHistory(vaccinationHistory)
                .allergyMedications(allergyMedications)
                .procedureDetail(procedureDetail)
                .build();
    }
}
