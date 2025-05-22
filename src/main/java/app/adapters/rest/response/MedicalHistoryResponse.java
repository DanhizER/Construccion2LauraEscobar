package app.adapters.rest.response;

import java.sql.Date;

import app.domain.models.MedicalHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryResponse {
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

    public static MedicalHistoryResponse fromMedicalHistory(MedicalHistory mh) {
        MedicalHistoryResponse response = new MedicalHistoryResponse();
        response.setMedicalHistoryId(mh.getMedicalHistoryID());
        response.setPetId(mh.getPet().getPetId());
        response.setVeterinarianId(mh.getVeterinarian().getDocument());
        response.setOrderId(mh.getOrder().getOrderId());
        response.setDateRegistration(mh.getDateRegistration());
        response.setReasonConsultation(mh.getReasonConsultation());
        response.setSymptomatology(mh.getSymptomatology());
        response.setDiagnosis(mh.getDiagnosis());
        response.setProcedure(mh.getProcedure());
        response.setMedication(mh.getMedication());
        response.setDrugDosage(mh.getDrugDosage());
        response.setVaccinationHistory(mh.getVaccinationHistory());
        response.setAllergyMedications(mh.getAllergyMedications());
        response.setProcedureDetail(mh.getProcedureDetail());
        response.setOrderCancellation(mh.isOrderCancellation());
        return response;
    }
}
