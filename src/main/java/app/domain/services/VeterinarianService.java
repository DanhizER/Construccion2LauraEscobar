package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import app.ports.MedicalHistoryPort;
import app.ports.OrderPort;
import app.ports.PersonPort;
import app.ports.PetPort;
import app.ports.UserAccountPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VeterinarianService {

	@Autowired
	private MedicalHistoryPort medicalHistoryPort;
	@Autowired
	private OrderPort orderPort;
	@Autowired
	private PetPort petPort;
	@Autowired
	private PersonPort personPort;
	@Autowired
	private UserAccountPort userAccountPort;
	
	//Creamos owner de la mascota
	public void ownerRegistrer(Person person) {
		try {
	    	if(personPort.existPerson(person.getDocument())) {
				log.error("Registro fallido /nYa existe una persona con esa cédula: {}", person.getDocument());
				throw new IllegalArgumentException("Ya existe una persona con esa cedula");
			}
			person.setRole(Role.USER);
			
			String[] nameParts = person.getName().trim().split("\\s+");
			String nombre = nameParts.length > 0 ? capitalize(nameParts[0]) : "";
			String apellido = nameParts.length > 1 ? capitalize(nameParts[1]) : "";
			String docStr = String.valueOf(person.getDocument());
			String lastTwo = docStr.length() >= 2 ? docStr.substring(docStr.length() - 2) : docStr;
			String username = nombre + apellido + lastTwo;
			String password = username; // La contraseña será igual al username

			personPort.savePerson(person);

			UserAccount userAccount = UserAccount.builder()
				.document(person.getDocument())
				.userName(username)
				.password(password)
				.role(Role.USER)
				.build();
			userAccountPort.saveUser(userAccount);

			log.info("Usuario Registrado: "+ person.getName());
		}catch(Exception e) {
			log.error("Error al registrar Usuario: {}", e.getMessage());
		}
	}

	//Creamos la orden medica
	@Transactional
	public void registerOrder(Order order) {
		if (order.getOrderId() != null && orderPort.findByOrderId(order.getOrderId()) != null) {
			log.error("Ya existe una orden con el ID " + order.getOrderId());
			throw new IllegalArgumentException("Ya existe una orden con el ID " + order.getOrderId());
		}
		orderPort.saveOrder(order);
		log.info("Orden medica generada exitosamente " + order);
	}

	//Cancelamos la orden medica
	@Transactional
	public void cancelOrder(long orderId, String reason) {
		Order order = orderPort.findByOrderId(orderId);
		if (order == null) {
			log.error("No existe una orden con el ID " + orderId);
			throw new IllegalArgumentException("No existe una orden con el ID " + orderId);
		}
		if(order.isOrderCancelled()) {
			log.error("La orden con el ID {} ya ha sido cancelada", orderId);
			throw new IllegalArgumentException("La orden con el ID " + orderId + " ya ha sido cancelada");
		}
		order.setOrderCancelled(true);
		orderPort.saveOrder(order);
		log.info("Orden cancelada exitosamente " + order);
	}
	
	//Registramos una historia clinica
	public void registerMedicalHistory(MedicalHistory medicalHistory) {
		UserAccount user = userAccountPort.findByDocument(medicalHistory.getVeterinarian().getDocument());
		if(user==null || !user.getRole().equals(Role.VETERINARIAN)) {
			log.error("No exise usuario veterinario con ese numero de documento {} ",medicalHistory.getVeterinarian().getDocument());
			throw new IllegalArgumentException("No exise usuario veterinario con ese numero de documento " + medicalHistory.getVeterinarian().getDocument());
		}	
		
		Pet pet = petPort.findByIdPet(medicalHistory.getPet().getPetId());
		if(pet == null) {
			log.error("Mascota con el ID {} no exixte",medicalHistory.getPet().getPetId());
			throw new IllegalArgumentException("No existe mascota con ID " + medicalHistory.getPet().getPetId());
		}
		
		Order order = null;
		if (medicalHistory.getOrder() != null && medicalHistory.getOrder().getOrderId() != null) {
			order = orderPort.findByOrderId(medicalHistory.getOrder().getOrderId());
			if(order == null) {
				log.error("Orden con id {} no existe para esta historia medica ", medicalHistory.getOrder().getOrderId());
				throw new IllegalArgumentException("Esta orden no existe para esta historia medica. Codigo de orden: " + medicalHistory.getOrder().getOrderId());
			}
			// SOLO valida la mascota asociada si hay orden
			if(pet.getPetId() != order.getPet().getPetId()) {
				log.error("La mascota con ID {} no esta asociada la orden {} ",medicalHistory.getPet().getPetId(),medicalHistory.getOrder().getOrderId());
				throw new IllegalArgumentException("La mascota con ID " + medicalHistory.getPet().getPetId() +" no esta asociada la orden "+ medicalHistory.getOrder().getOrderId());
			}
			medicalHistory.setVeterinarian(order.getVeterinarian());
			medicalHistory.setOrder(order);
		}		
		
		medicalHistoryPort.saveMedicalHistory(medicalHistory);
		log.info("Historia clinica generada exitosamente " + medicalHistory);
	}
	
	//Actualizamos la historia clinica
	//Buscar HC 
	public void updateMedicalHistory(MedicalHistory medicalHistory) {
		MedicalHistory medicalHistoryExists = medicalHistoryPort.findByIdMedicalHistory(medicalHistory.getMedicalHistoryID());
		if(medicalHistoryExists == null) {
			log.error("No existe una historia clinica con el ID " + medicalHistory.getMedicalHistoryID());
			throw new IllegalArgumentException("No existe una historia clinica con el ID " + medicalHistory.getMedicalHistoryID());
		}
		
		medicalHistoryExists.setVeterinarian(medicalHistory.getVeterinarian());	
		medicalHistoryExists.setSymptomatology(medicalHistory.getSymptomatology());	
		medicalHistoryExists.setDiagnosis(medicalHistory.getDiagnosis());
		medicalHistoryExists.setProcedure(medicalHistory.getProcedure());
		medicalHistoryExists.setProcedureDetail(medicalHistory.getProcedureDetail());
		medicalHistoryExists.setOrder(medicalHistory.getOrder());
		medicalHistoryExists.setMedication(medicalHistory.getMedication());
		medicalHistoryExists.setDrugDosage(medicalHistory.getDrugDosage());
		medicalHistoryExists.setVaccinationHistory(medicalHistory.getVaccinationHistory());
		medicalHistoryExists.setAllergyMedications(medicalHistory.getAllergyMedications());
		medicalHistoryExists.setOrderCancellation(medicalHistory.isOrderCancellation());

		medicalHistoryPort.saveMedicalHistory(medicalHistoryExists);
		
		log.info("Historia clinica actualizada exitosamente " + medicalHistoryExists);
	}
	
	//Se registra la mascota en el sistema
	@Transactional
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
			.characteristics(pet.getCharacteristics())
			.weight(pet.getWeight())
			.build();
	    petPort.savePet(newPet);
	    log.info("Mascota registrada exitosamente: {}", pet.getNamePet());
	}

	//Si la mascota ya esta registrada podemos actualizar su informacion
	public void updatePet(Pet updatePetInfo){
		if(!petPort.existsPetById(updatePetInfo.getPetId())) {
			log.error("Actualizacion fallida La mascota con ID {} no encontrada ", updatePetInfo.getPetId());
			throw new IllegalArgumentException("Mascota no encontrada.");
		}

		// Recupera la mascota actual para no perder campos no enviados
		Pet currentPet = petPort.findByIdPet(updatePetInfo.getPetId());
		if (currentPet == null) {
			throw new IllegalArgumentException("Mascota no encontrada.");
		}

		Pet updatePet = Pet.builder()
			.petId(updatePetInfo.getPetId())
			.namePet(updatePetInfo.getNamePet() != null ? updatePetInfo.getNamePet() : currentPet.getNamePet())
			.ownersId(updatePetInfo.getOwnersId() != null ? updatePetInfo.getOwnersId() : currentPet.getOwnersId())
			.age(updatePetInfo.getAge() != 0 ? updatePetInfo.getAge() : currentPet.getAge())
			.species(updatePetInfo.getSpecies() != null ? updatePetInfo.getSpecies() : currentPet.getSpecies())
			.race(updatePetInfo.getRace() != null ? updatePetInfo.getRace() : currentPet.getRace())
			.characteristics(updatePetInfo.getCharacteristics() != null ? updatePetInfo.getCharacteristics() : currentPet.getCharacteristics())
			.weight(updatePetInfo.getWeight() != 0 ? updatePetInfo.getWeight() : currentPet.getWeight())
			.build();

		petPort.updatePet(updatePet);
		log.info("Información actualizada exitosamente");

	}
	
	//Buscamos las ordenes medicas por ID de mascota
	public List<Order>	findPetOrders(Long petId){
		List<Order> orders = orderPort.findOrderbyPetId(petId);
		if (orders == null || orders.isEmpty()) {
			log.error("No se encontraron órdenes médicas para la mascota con ID {}", petId);
			throw new IllegalArgumentException("No se encontraron órdenes médicas para la mascota con ID " + petId);
		}
		log.info("Órdenes médicas para la mascota con ID {} encontradas", petId);
		return orders;		//Agrega este metodo a seller
	}

	
	//buscamos la historia clinica de la mascota
	public List <MedicalHistory> findAllMedicalHistoriesByPetId(Long petId){		
		Pet existingPet=petPort.findByIdPet(petId);
		if (existingPet==null) {
			log.error("Mascota con ID {} no encontrada",petId);
			throw new IllegalArgumentException("Mascota con ID "+petId+" no encontrada");
		}
		List<MedicalHistory> history=medicalHistoryPort.findPetMedicalHistories(petId);
		if(history==null || history.isEmpty()) {
			log.error("Historia clinica con ID de mascota {} no encontrada",petId);
			throw new IllegalArgumentException("Historia clinica con ID de mascota "+petId+" no encontrada");
		}
		log.info("Historia clinica de la mascota con ID {} ha sido encontrada",petId);
		return history;
	}

	// Método auxiliar para capitalizar la primera letra
	private String capitalize(String str) {
		if (str == null || str.isEmpty()) return "";
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
