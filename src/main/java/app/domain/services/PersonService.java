package app.domain.services;

import org.springframework.stereotype.Service;

import app.domain.models.Person;
import app.ports.PersonPort;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonService {
	private final PersonPort personPort;
		
	public PersonService(PersonPort personPort) {
		this.personPort = personPort;
	}

	//Validamos si la persona existe
	public boolean existPerson(long document){
		Person person= personPort.findByDocument(document);
		return person != null;
	}
	
	//Agregamos persona al sistema
	public void savePerson(Person person) throws Exception{
		if(existPerson(person.getDocument())) {
			throw new Exception("Ya existe una persona con esa cedula");
		}
		personPort.savePerson(person);
		System.out.println("La persona "+ person.getName()+" ha sido creada exitosamente");
	}
	
	//Buscamos a la persona por su documento
	public Person findByDocument(long document) throws Exception{
		Person person= personPort.findByDocument(document);
		if (person==null) {
			throw new Exception("Persona no encontrada");
		}
		return person;
	}
	
	//eliminamos a la persona por su documento
	public void deleteByDocument(long document) throws Exception{
		if(!existPerson(document)) {
			throw new Exception("Persona no encontrada");
		}
		personPort.deleteByDocument(document);
		System.out.println("La persona ha sido eliminada exitosamente");
	}
}
