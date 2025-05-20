package app.adapters.medicalHistory.repository;

import app.adapters.invoice.entity.InvoiceEntity;
import app.adapters.medicalHistory.entity.MedicalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public class MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, Long> {
}
