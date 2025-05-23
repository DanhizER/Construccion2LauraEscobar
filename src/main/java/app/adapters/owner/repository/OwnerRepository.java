package app.adapters.owner.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.owner.entity.OwnerEntity;


public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {
    boolean existsByDocument(Long document);
    OwnerEntity findByDocument(Long document);
    void deleteByDocument(Long document);
}
