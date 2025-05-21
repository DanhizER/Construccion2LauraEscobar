package app.adapters.medicalHistory;

import app.adapters.medicalHistory.entity.MedicalHistoryEntity;
import app.adapters.medicalHistory.repository.MedicalHistoryRepository;
import app.adapters.order.entity.OrderEntity;
import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.ports.MedicalHistoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalHistoryAdapter implements MedicalHistoryPort {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public void saveMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistoryRepository.save(new MedicalHistoryEntity(medicalHistory));
    }

    @Override
    public void updateMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistoryRepository.save(new MedicalHistoryEntity(medicalHistory));
    }

    @Override
    public List<Order> findPetOrders(Long petId) {
        return medicalHistoryRepository.findByPet_PetId(petId).stream()
                .map(MedicalHistoryEntity::getOrder) // Asumiendo que tienes un solo Order por historia
                .filter(orderEntity -> orderEntity != null)
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalHistory> findPetMedicalHistories(Long petId) {
        return medicalHistoryRepository.findByPet_PetId(petId).stream()
                .map(MedicalHistoryEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalHistory findByIdMedicalHistory(Long medicalHistoryId) {
        return medicalHistoryRepository.findById(medicalHistoryId)
                .map(MedicalHistoryEntity::toDomain)
                .orElse(null);
    }
}
