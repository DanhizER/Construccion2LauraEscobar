package app.ports;

import app.domain.models.Person;
import app.domain.models.UserAccount;

public interface AdminPort {
	void registerSeller(Person person);
	void registerVeterinarian(Person person);
	void deleteUser(long document, UserAccount userAccount);
}
