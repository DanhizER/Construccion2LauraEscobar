package app.adapters.person;

import org.springframework.beans.factory.annotation.Autowired;

import app.adapters.person.entity.PersonEntity;
import app.adapters.person.repository.PersonRepository;
import app.domain.models.Person;
import app.ports.PersonPort;

public class PersonAdapter implements PersonPort {
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public boolean existPerson(long document){
		return personRepository.existByDocument(document);		
	}
	
	@Override
	public void savePerson(Person person){
		PersonEntity personEntity = new PersonEntity(person);
		personRepository.save(personEntity);
		person.setDocument(personEntity.getDocument());
	}
	
	@Override
	public Person findByDocument(long document){
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
	public void deleteByDocument(long document) {
		PersonEntity personEntity = personRepository.findByDocument(document);
		personRepository.delete(personEntity);
		
	}
}
