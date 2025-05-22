package app.adapters.rest.utils;

import app.domain.models.UserAccount;

public class UserValidator {

    // Validación de objeto completo (en grupo)
    public static void validate(UserAccount user) throws Exception {
        if (user == null) throw new Exception("El usuario no puede ser nulo");
        PersonValidator.documentValidator(user.getDocument());
        PersonValidator.nameValidator(user.getName());
        PersonValidator.ageValidator(user.getAge());
        PersonValidator.roleValidator(user.getRole());
        // Valida los campos de usuario
        usernameValidator(user.getUserName());
        passwordValidator(user.getPassword());
    }

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
