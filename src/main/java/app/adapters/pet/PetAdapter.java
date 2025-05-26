package app.adapters.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.domain.models.Pet;
import app.ports.PetPort;
import lombok.extern.slf4j.Slf4j;
import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PetAdapter implements PetPort {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public void savePet(Pet pet) {
        log.info("Buscando owner con documento: {}", pet.getOwnersId());
        OwnerEntity owner = ownerRepository.findByDocument(pet.getOwnersId());
        log.info("Resultado de búsqueda de owner: {}", owner);
        if (owner == null) {
            throw new RuntimeException("ID de dueño inválido");
        }
        PetEntity petEntity = new PetEntity(pet);
        petEntity.setOwnersId(owner); // Asigna el owner real
        log.info("Antes de guardar mascota: {}", petEntity);
        petRepository.save(petEntity);
        log.info("Después de guardar mascota: {}", petEntity);
    }

    @Override
    public void updatePet(Pet pet) {
        PetEntity existingEntity = petRepository.findById(pet.getPetId()).orElse(null);
        OwnerEntity ownerEntity = null;
        if (pet.getOwnersId() != null) {
            ownerEntity = ownerRepository.findByDocument(pet.getOwnersId());
        } else if (existingEntity != null) {
            ownerEntity = existingEntity.getOwnersId();
        }
        PetEntity petEntity = new PetEntity(pet);
        petEntity.setOwnersId(ownerEntity); // Siempre asigna el owner, nunca null si ya existía
        petRepository.save(petEntity);
    }

    @Override
    public boolean existsPetById(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    public Pet findByIdPet(Long id) {
        return petRepository.findById(id)
                .map(PetEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        List<PetEntity> entities = petRepository.findByOwnersIdDocument(ownerId);
        return entities.stream()
                .map(PetEntity::toDomain)
                .collect(Collectors.toList());
    }
}
