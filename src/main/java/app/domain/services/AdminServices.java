package app.domain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.Person;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import app.ports.PersonPort;
import app.ports.UserAccountPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@NoArgsConstructor
@Service
@Slf4j
public class AdminServices{

	@Autowired
	private  PersonPort personPort;
    @Autowired
    private UserAccountPort userAccountPort;

    //Como estas funciones son exclusivas del admin entonces
	// verificamos si es el admin el que va a realizar algunad e estas funciones
	public boolean isAdmin(UserAccount user){
		Person person= personPort.findByDocument(user.getDocument());
		if(person==null){
			log.warn("Validacion Fallida: No se encontro persona con documento {}", user.getDocument());
			return false;
		}
		if (!Role.ADMIN.equals(person.getRole())) {
			log.warn("Acceso Denegado: el usuario con documento {} no tiene rol de ADMINISTRADOR", user.getDocument());
			return false;
		}
		log.info("Usuario con documento {} validado como ADMINISTRADOR", user.getDocument());
		return true;
	}

    public void registerSeller(UserAccount user){
	    if(!isAdmin(user)){
			log.error("Solo los administradores pueden registrar vendedores");
			throw new IllegalArgumentException("Solo los administradores pueden registrar vendedores");
		}
	    if(personPort.existPerson(user.getDocument())) {
	    	log.error("Registro fallido Ya existe una persona con esa cédula: {}", user.getDocument());
	    	throw new IllegalArgumentException("Ya existe una persona con esa cedula");
		}
			user.setRole(Role.SELLER);
			personPort.savePerson(user);
			userAccountPort.saveUser(user);
			log.info("Vendedor/@ Registrado: "+ user.getName());
    }
	
    public void registerVeterinarian(UserAccount user) {
		if(!isAdmin(user)){
			log.error("Solo los administradores pueden registrar Veterinarios");
			throw new IllegalArgumentException("Solo los administradores pueden registrar Veterinarios");
		}
		if(personPort.existPerson(user.getDocument())) {
			log.error("Registro fallido /nYa existe una persona con esa cédula: {}", user.getDocument());
			throw new IllegalArgumentException("Ya existe una persona con esa cedula");
		}
		user.setRole(Role.VETERINARIAN);
		personPort.savePerson(user);
		userAccountPort.saveUser(user);
		log.info("Veterinari@ Registrado: "+ user.getName());
    }
	
    public void deleteUser(long document, UserAccount user){
		if(!isAdmin(user)){
			log.error("Solo los administradores pueden eliminar usuarios");
			throw new IllegalArgumentException("Solo los administradores pueden eliminar usuarios");
		}
		if(!personPort.existPerson(document)) {
	   		log.error("Eliminacion Fallida: el usuario con cedula {} no fue encontrado",document);
	        throw new IllegalArgumentException("Usuario no encontrado");
	   	}
	   	personPort.deleteByDocument(document);
	   	userAccountPort.deleteUser(document);
	   	log.info("El usuario con Identificaicon {} ha sido eliminado", document);
    }
	
}
