package app.ports;

import app.domain.models.Person;

public interface PersonPort {
	 public boolean existPerson(Long document);
	 public void savePerson(Person person);
	 public Person findByDocument(Long document);
	 public void deleteByDocument(Long document);
}
