package app.adapters.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.invoice.entity.InvoiceEntity;
import app.domain.models.Invoice;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
     List<Invoice> findByOwnerId(Long ownerDoc);
}