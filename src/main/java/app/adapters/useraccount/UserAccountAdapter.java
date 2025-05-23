package app.adapters.useraccount;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import app.adapters.useraccount.entity.UserAccountEntity;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.domain.models.Person;
import app.domain.models.UserAccount;
import app.ports.UserAccountPort;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class UserAccountAdapter implements UserAccountPort {
    
    @Autowired
    private UserAccountRepository userAccountRepository;
    

    @Override
    public void registerUser(UserAccount userAccount, Person person) {
        userAccountRepository.save(new UserAccountEntity(userAccount));
    }

    @Override
    public UserAccount login(String username, String password) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity != null && entity.getPassword().equals(password)) {
            return entity.toDomain();
        }
        return null;
    }


    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
       UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity != null && entity.getPassword().equals(oldPassword)) {
            entity.setPassword(newPassword);
            userAccountRepository.save(entity);
        }
    }


    @Override
    public void saveUser(UserAccount user) {
        userAccountRepository.save(new UserAccountEntity(user));
    }


    @Override
    public void deleteUser(Long document) {
        log.info("Eliminando usuario con documento: {}", document);
        userAccountRepository.deleteByDocument(document);   
    }


    @Override
    public UserAccount findByUserName(String username) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        return entity != null ? entity.toDomain() : null;
    }


    @Override
    public List<UserAccount> findAllUsers() {
        return userAccountRepository.findAll()
                .stream()
                .map(UserAccountEntity::toDomain)
                .collect(Collectors.toList());
    } 
    
    @Override
    public UserAccount findByDocument(Long document) {
        UserAccountEntity entity = userAccountRepository.findByDocument(document);
        return entity != null ? entity.toDomain() : null;
    }
    
}
