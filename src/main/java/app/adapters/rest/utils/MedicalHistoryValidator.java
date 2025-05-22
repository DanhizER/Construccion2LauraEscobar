package app.adapters.rest.utils;

import app.domain.models.MedicalHistory;

public class MedicalHistoryValidator {

    public static void validate(MedicalHistory mh) throws Exception {
        if (mh == null) throw new Exception("La historia clínica no puede ser nula");
        medicalHistoryIdValidator(mh.getMedicalHistoryID());
        petIdValidator(mh.getPet().getPetId());
        veterinarianIdValidator(mh.getVeterinarian().getDocument());
        reasonConsultationValidator(mh.getReasonConsultation());
        diagnosisValidator(mh.getDiagnosis());
        procedureValidator(mh.getProcedure());
        vaccinationHistoryValidator(mh.getVaccinationHistory());

    }

    public static Long medicalHistoryIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de historia clínica inválido");
        return value;
    }

    public static Long petIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de mascota inválido");
        return value;
    }

    public static Long veterinarianIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de veterinario inválido");
        return value;
    }

    public static String reasonConsultationValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Motivo de consulta inválido");
        return value;
    }

    public static String diagnosisValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Diagnóstico inválido");
        return value;
    }

    public static String procedureValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Procedimiento inválido");
        return value;
    }

    public static String vaccinationHistoryValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Historia de vacunación inválida");
        return value;
    }
}
