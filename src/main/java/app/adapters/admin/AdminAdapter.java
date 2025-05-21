package app.adapters.admin;

import app.domain.models.Person;
import app.domain.models.UserAccount;
import app.ports.AdminPort;
import app.adapters.owner.repository.OwnerRepository;
import app.adapters.person.entity.PersonEntity;
import app.adapters.person.repository.PersonRepository;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.adapters.useraccount.entity.UserAccountEntity;
import app.domain.types.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminAdapter implements AdminPort {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void registerSeller(Person person) {
        person.setRole(Role.SELLER);
        personRepository.save(new PersonEntity(person));
        userAccountRepository.save(new UserAccountEntity((UserAccount) person));
    }

    @Override
    public void registerVeterinarian(Person person) {
        person.setRole(Role.VETERINARIAN);
        personRepository.save(new PersonEntity(person));
        userAccountRepository.save(new UserAccountEntity((UserAccount) person));
    }

    @Override
    public void deleteUser(Long document) {
        personRepository.deleteById(document);
        ownerRepository.deleteById(document);
        userAccountRepository.deleteById(document);
    }
}
