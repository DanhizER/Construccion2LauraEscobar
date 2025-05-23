package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.adapters.owner.OwnerAdapter;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import app.ports.PersonPort;
import app.ports.PetPort;
import app.ports.UserAccountPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OwnerService {	
	
	@Autowired
	private PetPort petPort;
	@Autowired
	private PersonPort personPort;
	@Autowired
	private UserAccountPort userAccountPort;
	@Autowired
	private OwnerAdapter ownerAdapter;
	
	//Validamos si la mascota existe en el sistema
	public boolean existPetById(long id) {
		Pet pet=petPort.findByIdPet(id);
		return pet!=null;
	}
	
	//Registro de la mascota solo datos basicos
	public void registerPet(Pet pet) throws Exception {
		if (pet.getPetId() != null && petPort.existsPetById(pet.getPetId())) {
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
		ownerAdapter.savePet(newPet);
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
		List<Pet> pets=petPort.findByOwnerId(ownerDoc);
		log.info("Mascotas encontradas para el dueño con cédula {}: {}", ownerDoc, pets.size());
		return pets;
	}

	public void registerOwner(Person owner, String username, String password) {
		if (personPort.existPerson(owner.getDocument())) {
			log.error("Registro fallido: Ya existe una persona con esa cédula: {}", owner.getDocument());
			throw new IllegalArgumentException("Ya existe una persona con esa cédula.");
		}
		if (userAccountPort.findByUserName(username) != null) {
			log.error("Registro fallido: Ya existe un usuario con ese username: {}", username);
			throw new IllegalArgumentException("Ya existe un usuario con ese username.");
		}
		owner.setRole(Role.USER);
		personPort.savePerson(owner);

		UserAccount userAccount = UserAccount.builder()
			.document(owner.getDocument())
			.userName(username)
			.password(password)
			.role(Role.USER)
			.build();
		userAccountPort.saveUser(userAccount);

		log.info("Dueño registrado exitosamente: {} | Usuario: {}", owner.getName(), username);
	}
	
}
