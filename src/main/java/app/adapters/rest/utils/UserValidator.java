package app.adapters.rest.utils;

import org.springframework.stereotype.Component;

import app.domain.models.UserAccount;

@Component
public class UserValidator {

    // Validadores individuales
    public static String usernameValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Username inválido");
        return value;
    }

    public static String passwordValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Contraseña inválida");
        return value;
    }
}
