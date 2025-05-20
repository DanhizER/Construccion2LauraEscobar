package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.UserAccount;
import app.domain.models.Person;
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
    private PersonPort personPort;
    @Autowired
    private UserAccountPort userAccountPort;
    
    public void registerSeller(Person person, String userName, String password){
	    if(personPort.existPerson(person.getDocument())) {
	    	log.error("Registro fallido, Ya existe una persona con esa cédula: {}", person.getDocument());
	    	throw new IllegalArgumentException("Ya existe una persona con esa cedula");
		}
			person.setRole(Role.SELLER);
			personPort.savePerson(person);

			UserAccount userAccount = UserAccount.builder()
					.document(person.getDocument())
					.name(person.getName())
					.age(person.getAge())
					.role(Role.SELLER)
					.userName(userName)
					.password(password)
					.build();
					
			userAccountPort.saveUser(userAccount);
			log.info("Vendedor/@ Registrado: "+ person.getName());		
    }
	
    public void registerVeterinarian(Person person, String userName, String password) {
	    	if(personPort.existPerson(person.getDocument())) {
				log.error("Registro fallido, Ya existe una persona con esa cédula: {}", person.getDocument());
				throw new IllegalArgumentException("Ya existe una persona con esa cedula");
			}
			person.setRole(Role.VETERINARIAN);
			personPort.savePerson(person);

			UserAccount userAccount = UserAccount.builder()
					.document(person.getDocument())
					.name(person.getName())
					.age(person.getAge())
					.role(Role.VETERINARIAN)
					.userName(userName)
					.password(password)
					.build();
					
			userAccountPort.saveUser(userAccount);			
			log.info("Veterinari@ Registrado: "+ person.getName());
    }
	
    public void deleteUser(long document){
	    	if(!personPort.existPerson(document)) {
	    		log.error("Eliminacion Fallida /n el usuario con cedula {} no fue encontrado",document);
	            throw new IllegalArgumentException("Usuario no encontrado");
	    	}
	    	personPort.deleteByDocument(document);
	    	userAccountPort.deleteUser(document);
	    	log.info("El usuario con Identificaicon {} ha sido eliminado", document);
    }
	
}
