package app.adapters.useraccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.adapters.useraccount.entity.UserAccountEntity;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.domain.models.UserAccount;
import app.ports.UserAccountPort;

public class UserAccountAdapter implements UserAccountPort {
    @Autowired
    private UserAccountRepository userAccountRepository;
    

    @Override
    public void registerUser(UserAccount userAccount) {
        UserAccountEntity entity = adapterAccount(userAccount);
        userAccountRepository.save(entity);
    }

    private UserAccountEntity adapterAccount(UserAccount userAccount) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername(userAccount.getUserName());
        entity.setPassword(userAccount.getPassword());
        entity.setRole(userAccount.getRole());
        return entity;
    }

    @Override
    public UserAccount login(String username, String password) {
        UserAccountEntity entity = userAccountRepository.findByUsername(username);
        if (entity != null && entity.getPassword().equals(password)) {
            return null;
        }
        return adapterAccount(entity);
    }


    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }


    @Override
    public void saveUser(UserAccount user) {
        UserAccountEntity entity = new UserAccountEntity(user);
        userAccountRepository.save(entity);
    }


    @Override
    public void deleteUser(String username) {
        userAccountRepository.deleteByUsername(username);
    }


    @Override
    public UserAccount findByUserName(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserName'");
    }


    @Override
    public List<UserAccount> findAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsers'");
    }
    private 
    
    
}
