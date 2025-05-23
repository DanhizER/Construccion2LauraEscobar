package app.adapters.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.adapters.person.entity.PersonEntity;
import app.adapters.person.repository.PersonRepository;
import app.domain.models.Person;
import app.ports.PersonPort;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
public class PersonAdapter implements PersonPort {
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public boolean existPerson(Long document){
		return personRepository.existsByDocument(document);		
	}
	
	@Override
	public void savePerson(Person person){
		log.info("Guardando persona en adapter: {}", person);
		personRepository.save(PersonEntity.fromDomain(person));
		log.info("Persona guardada en la base de datos");
	}
	
	@Override
	public Person findByDocument(Long document){
		PersonEntity personEntity = personRepository.findByDocument(document);
		return adapterPerson(personEntity);
	}
	
	private Person adapterPerson(PersonEntity personEntity) {
		Person person= new Person();
		person.setDocument(personEntity.getDocument());
		person.setName(personEntity.getName());
		person.setAge(personEntity.getAge());
		person.setRole(personEntity.getRole());
		return person;
	}

	@Override
	public void deleteByDocument(Long document) {
		log.info("Eliminando persona con documento: {}", document);
		personRepository.deleteByDocument(document);
		
	}
}
