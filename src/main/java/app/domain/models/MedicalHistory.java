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
@Builder
@AllArgsConstructor
public class MedicalHistory {
	private Pet pet;//info de la mascota (ID)
	private Long medicalHistoryID;//ID de la historia medica
	private Date dateRegistration;
	private UserAccount veterinarian;//info del veterinario(ID)
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
