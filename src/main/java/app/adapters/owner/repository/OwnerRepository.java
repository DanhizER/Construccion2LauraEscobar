package app.adapters.owner.repository;

import app.adapters.invoice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.owner.entity.OwnerEntity;
import app.adapters.pet.entity.PetEntity;

public class OwnerRepository extends JpaRepository<InvoiceEntity, Long> {
    boolean existsByDocument(Long document);
    OwnerEntity findByDocument(Long document);
}
