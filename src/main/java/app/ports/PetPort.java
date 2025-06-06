package app.ports;

import java.util.List;

import app.domain.models.Pet;

public interface PetPort {
	void savePet(Pet pet);
    void updatePet(Pet pet);
    boolean existsPetById(Long id);
    Pet findByIdPet(Long id);
    List<Pet> findByOwnerId(Long ownerId);
}
 