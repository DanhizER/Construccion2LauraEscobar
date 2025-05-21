package app.adapters.medicalHistory.repository;

import app.adapters.medicalHistory.entity.MedicalHistoryEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
    List<MedicalHistoryEntity> findByPet_PetId(Long petId);
}
