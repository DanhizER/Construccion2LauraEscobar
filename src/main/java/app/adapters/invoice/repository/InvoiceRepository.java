package app.adapters.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.invoice.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
     List<InvoiceEntity> findByOwner_Document(Long ownerDoc);
}