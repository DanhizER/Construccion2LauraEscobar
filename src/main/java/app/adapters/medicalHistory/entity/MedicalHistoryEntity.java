package app.adapters.medicalHistory.entity;

import app.adapters.order.entity.OrderEntity;
import app.adapters.pet.entity.PetEntity;
import app.adapters.useraccount.entity.UserAccountEntity;
import app.domain.models.MedicalHistory;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "medical_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private Long medicalHistoryID;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "petid")
    private PetEntity pet;
    @Column(name = "date_registration")
    private Date dateRegistration;
    @ManyToOne
    @JoinColumn(name = "veterinarian", referencedColumnName="document")
    private UserAccountEntity veterinarian;
    @Column(name = "reason_consultation")
    private String reasonConsultation;
    @Column(name = "symptomatology")
    private String symptomatology;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "medical_procedure")
    private String procedure;
    @Column(name = "medication")
    private String medication;
    @Column(name = "drug_dosage")
    private String drugDosage;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;
    @Column(name = "vaccine")
    private String vaccinationHistory;
    @Column(name = "allergy_medications")
    private String allergyMedications;
    @Column(name = "procedure_detail")
    private String procedureDetail;
    @Column(name = "order_Status")
    private boolean orderCancellation;

    public MedicalHistoryEntity(MedicalHistory medicalHistory) {
        this.medicalHistoryID = medicalHistory.getMedicalHistoryID();
        this.pet = PetEntity.builder().petId(medicalHistory.getPet() != null ? medicalHistory.getPet().getPetId() : null).build();
        this.dateRegistration = medicalHistory.getDateRegistration();
        this.veterinarian = UserAccountEntity.builder().document(medicalHistory.getVeterinarian() != null ? medicalHistory.getVeterinarian().getDocument() : null).build();
        this.reasonConsultation = medicalHistory.getReasonConsultation();
        this.symptomatology = medicalHistory.getSymptomatology();
        this.diagnosis = medicalHistory.getDiagnosis();
        this.procedure = medicalHistory.getProcedure();
        this.medication = medicalHistory.getMedication();
        this.drugDosage = medicalHistory.getDrugDosage();
        this.order = medicalHistory.getOrder() != null ? new OrderEntity(medicalHistory.getOrder()) : null;
        this.vaccinationHistory = medicalHistory.getVaccinationHistory();
        this.allergyMedications = medicalHistory.getAllergyMedications();
        this.procedureDetail = medicalHistory.getProcedureDetail();
        this.orderCancellation = medicalHistory.isOrderCancellation();
    }

    public MedicalHistory toDomain() {
        return MedicalHistory.builder()
                .medicalHistoryID(this.medicalHistoryID)
                .pet(this.pet != null ? this.pet.toDomain() : null)
                .dateRegistration(this.dateRegistration)
                .veterinarian(this.veterinarian != null ? this.veterinarian.toDomain() : null)
                .reasonConsultation(this.reasonConsultation)
                .symptomatology(this.symptomatology)
                .diagnosis(this.diagnosis)
                .procedure(this.procedure)
                .medication(this.medication)
                .drugDosage(this.drugDosage)
                .order(this.order != null ? this.order.toDomain() : null)
                .vaccinationHistory(this.vaccinationHistory)
                .allergyMedications(this.allergyMedications)
                .procedureDetail(this.procedureDetail)
                .orderCancellation(this.orderCancellation)
                .build();
    }
}
