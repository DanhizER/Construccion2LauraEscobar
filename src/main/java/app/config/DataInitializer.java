package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.adapters.useraccount.entity.UserAccountEntity;
import app.adapters.useraccount.repository.UserAccountRepository;
import app.domain.types.Role;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void run(String... args) {
        String adminUsername = "MaoD";
        UserAccountEntity admin = userAccountRepository.findByUsername(adminUsername);
        if (admin == null) {
            admin = UserAccountEntity.builder()
                .document(1031181644L)
                .username(adminUsername)
                .password("admin123")
                .role(Role.ADMIN)
                .build();
            userAccountRepository.save(admin);
            log.info("Admin creado");
            System.out.println("Admin creado");
        } else {
            admin.setPassword("admin123");
            admin.setRole(Role.ADMIN);
            admin.setDocument(1031181644L);
            userAccountRepository.save(admin);
            log.info("Admin ya existe, se actualiza");
            System.out.println("Admin actualizado");
        }
    }
}
