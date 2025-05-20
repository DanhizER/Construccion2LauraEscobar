package app.adapters.medicalHistory.entity;

import app.adapters.pet.entity.PetEntity;
import app.adapters.useraccount.entity.UserAccountEntity;
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
    private Long medicalHistoryID;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    private PetEntity pet;
    @Column(name = "date_registration")
    private Date dateRegistration;
    @ManyToMany
    @JoinColumn(name = "veterinarian", referencedColumnName="document")
    private UserAccountEntity veterinarian;
    private String reasonConsultation;
    private String symptomatology;
    private String diagnosis;
    private String procedure;
    private String medication;
    private String drugDosage;
    private Order order;//info de la order(ID)
    private String vaccinationHistory;
    private String allergyMedications;
    private String procedureDetail;
    private boolean orderCancellation;
}
