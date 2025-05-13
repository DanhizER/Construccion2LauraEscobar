package app.adapters.useraccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.adapters.useraccount.entity.UserAccountEntity;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long>{
    UserAccountEntity findByUsername(String username);
    UserAccountEntity findByDocument(long document);
}
