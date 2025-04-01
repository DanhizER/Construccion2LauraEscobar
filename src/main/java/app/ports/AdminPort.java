package app.ports;

import app.domain.models.Person;

public interface AdminPort {
	void registerSeller(Person person);
	void registerVeterinarian(Person person);
	void deleteUser(long document);
}
