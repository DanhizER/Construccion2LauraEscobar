package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.Pet;
import app.ports.PetPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OwnerService {	
	
	@Autowired
	private PetPort petPort;
	
	//Validamos si la mascota existe en el sistema
	public boolean existPetById(long id) {
		Pet pet=petPort.findByIdPet(id);
		return pet!=null;
	}
	
	//Registro de la mascota solo datos basicos
	public void registerPet(Pet pet){
	    if (petPort.existsPetById(pet.getPetId())) {
	        log.error("Registro fallido: La mascota con ID {} ya está registrada", pet.getPetId());
	        throw new IllegalArgumentException("La mascota ya está registrada.");
	    }
	    Pet newPet = Pet.builder()
	        .namePet(pet.getNamePet())
	        .ownersId(pet.getOwnersId())
	        .age(pet.getAge())
	        .species(pet.getSpecies())
	        .race(pet.getRace())
	        .build();
	    petPort.savePet(newPet);
	    log.info("Mascota registrada exitosamente: {}", newPet.getNamePet());
		
	}
	
	//Buscamos una mascota especifica del dueño por el ID de la mascota
	public Pet findPetById(long id) {
		Pet pet= petPort.findByIdPet(id);
		if (pet==null) {
			throw new IllegalArgumentException("Mascota no encontrada");
		}
		log.info("Mascota {} ha sido encontrada",pet.getNamePet());
		return pet;
	}
	
	//Actualizamos informacion basica de la mascota
	public void updatePet(Pet pet) {
		if(!petPort.existsPetById(pet.getPetId())) {
			log.error("Actualizacion fallida La mascota con ID {} no encontrada ", pet.getPetId());
	        throw new IllegalArgumentException("Mascota no encontrada.");
		}
			
		Pet updatePet= Pet.builder()
	        .namePet(pet.getNamePet())
	        .ownersId(pet.getOwnersId())
	        .age(pet.getAge())
	        .species(pet.getSpecies())
	        .race(pet.getRace())
	        .build();
		petPort.updatePet(updatePet);
		log.info("Mascota actualizada exitosamente: {}", updatePet.getNamePet());	
	}
	
	//Listamos todas las mascotas del dueño
	public List<Pet> listOwnerPets(long ownerDoc){
		List<Pet> pets=petPort.findByOwnerCedula(ownerDoc);
		log.info("Mascotas encontradas para el dueño con cédula {}: {}", ownerDoc, pets.size());
		return pets;
	}
	
}
