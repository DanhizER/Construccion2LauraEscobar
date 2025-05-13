package app.adapters.useraccount;

import java.util.ArrayList;
import java.util.List;

import app.adapters.person.entity.PersonEntity;
import app.adapters.person.repository.PersonRepository;
import app.domain.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.adapters.useraccount.entity.UserAccountEntity;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.domain.models.UserAccount;
import app.ports.UserAccountPort;

@Service
public class UserAccountAdapter implements UserAccountPort {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PersonRepository personRepository;

    public boolean existsUser(String username) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        return entity != null;
    }

    @Override
    public void saveUser(UserAccount user) {
        if (!personRepository.existByDocument(user.getDocument())) { //Verifica si la persona ya existe
            PersonEntity personEntity = personEntityAdapter(user);
            personRepository.save(personEntity);
        }
        UserAccountEntity entity = userAccountEntityAdapter(user);
        userAccountRepository.save(entity);
    }

    @Override
    public UserAccount login(String username, String password) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity != null && entity.getPassword().equals(password)) {
            return userAccountAdapter(entity);
        }
        throw new IllegalArgumentException("Nombre de usuario o Contraseña incorrectos");
    }


    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity == null || !entity.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Usuario no encontrado o contraseña incorrecta");
        }
        entity.setPassword(newPassword);
        userAccountRepository.save(entity);
    }

    @Override
    public void deleteUser(Long document) {
        UserAccountEntity entity = userAccountRepository.findByDocument(document);
        if  ( entity == null) {
            throw new IllegalArgumentException("Usuario con numero de identificacion "+document+" no existe");
        }
        userAccountRepository.delete(entity);
    }

    @Override
    public UserAccount findByUserName(String username) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity == null) {
            throw new IllegalArgumentException("Usuario con nombre de usuario "+username+" no existe");
        }
        return userAccountAdapter(entity);
    }   

    @Override
    public UserAccount findByDocument(Long document) {
		UserAccountEntity userEntity = userAccountRepository.findByDocument(document);
        if  (userEntity == null) {
            throw new IllegalArgumentException("No se encontro usuario con ese numero documento");
        }
		return userAccountAdapter(userEntity);
    }

    @Override
    public List<UserAccount> findAllUsers() {
        List<UserAccountEntity> usersEntity = userAccountRepository.findAll();
        List<UserAccount> users = new ArrayList<UserAccount>();
        for(UserAccountEntity userEntity : usersEntity) {
            users.add(userAccountAdapter(userEntity));
        }
        return users;
    }

    private UserAccount userAccountAdapter(UserAccountEntity userEntity) {
        if(userEntity == null){
            return null;
        }
        PersonEntity personEntity = userEntity.getPerson();
        UserAccount user = new UserAccount();

        user.setUserId(userEntity.getUserId());
        user.setUserName(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRole());

        if (personEntity != null) {
            user.setDocument(personEntity.getDocument());
            user.setName(personEntity.getName());
            user.setAge(personEntity.getAge());
            user.setRole(personEntity.getRole());
        }
        return user;
    }

    private UserAccountEntity userAccountEntityAdapter(UserAccount userAccount) {
        if(userAccount == null){
            return null;
        }
        UserAccountEntity userAccountEntity = new UserAccountEntity();

        userAccountEntity.setUserId(userAccount.getUserId());
        userAccountEntity.setUsername(userAccount.getUserName());
        userAccountEntity.setPassword(userAccount.getPassword());
        userAccountEntity.setRole(userAccount.getRole());

        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocument(userAccount.getDocument());
        personEntity.setName(userAccount.getName());
        personEntity.setAge(userAccount.getAge());
        personEntity.setRole(userAccount.getRole());

        userAccountEntity.setPerson(personEntity);

        return userAccountEntity;
    }

    private PersonEntity personEntityAdapter(Person person) {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setAge(person.getAge());
        personEntity.setRole(person.getRole());
        return personEntity;
    }
    
    
}
