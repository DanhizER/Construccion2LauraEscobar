package app.domain.models;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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
	
	public MedicalHistory(Pet pet,Long medicalHistoryID,Date dateRegistration, UserAccount veterinarian, String reasonConsultation, String symptomatology,
			String diagnosis, String procedure, String medication, String drugDosage, Order order,
			String vaccinationHistory, String allergyMedications, String procedureDetail, boolean orderCancellation) {
		this.pet=pet;
		this.medicalHistoryID=medicalHistoryID;
		this.dateRegistration = dateRegistration;
		this.veterinarian = veterinarian;
		this.reasonConsultation = reasonConsultation;
		this.symptomatology = symptomatology;
		this.diagnosis = diagnosis;
		this.procedure = procedure;
		this.medication = medication;
		this.drugDosage = drugDosage;
		this.order = order;
		this.vaccinationHistory = vaccinationHistory;
		this.allergyMedications = allergyMedications;
		this.procedureDetail = procedureDetail;
		this.orderCancellation = orderCancellation;
	}	
	
}
