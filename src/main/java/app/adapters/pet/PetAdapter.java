package app.adapters.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.domain.models.Pet;
import app.ports.PetPort;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetAdapter implements PetPort {

    @Autowired
    private PetRepository petRepository;

    @Override
    public void savePet(Pet pet) {
        petRepository.save(new PetEntity(pet));
    }

    @Override
    public void updatePet(Pet pet) {
        petRepository.save(new PetEntity(pet));
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
