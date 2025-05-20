package app.adapters.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.domain.models.Pet;
import app.ports.PetPort;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
    public Pet findByIdPet(long id) {
        return petRepository.findById(id)
                .map(PetEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return petRepository.findByOwnersId(ownerId)
                .stream()
                .map(PetEntity::toDomain)
                .collect(Collectors.toList());
    }
}
