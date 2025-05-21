package app.adapters.pet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.adapters.pet.entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findByOwnersIdDocument(Long ownersId);
}