package app.ports;

import java.util.List;

import app.domain.models.MedicalHistory;
import app.domain.models.Order;

public interface MedicalHistoryPort {
    void saveMedicalHistory(MedicalHistory medicalHistory);
    void updateMedicalHistory(MedicalHistory medicalHistory);
    List<Order> findPetOrders(Long petId);
    List<MedicalHistory> findPetMedicalHistories(Long petId);    
    MedicalHistory findByIdMedicalHistory(Long medicalHistoryId);
}
