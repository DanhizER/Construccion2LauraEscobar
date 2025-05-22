package app.adapters.rest.utils;

import app.domain.models.Person;
import app.domain.types.Role;

public class AdminValidator {

    public static void validate(Person admin, String username, String password) throws Exception {
        if (admin == null) throw new Exception("El administrador no puede ser nulo");
        PersonValidator.documentValidator(admin.getDocument());
        PersonValidator.nameValidator(admin.getName());
        PersonValidator.ageValidator(admin.getAge());
        PersonValidator.roleValidator(admin.getRole());
        usernameValidator(username);
        passwordValidator(password);
        if (admin.getRole() != Role.ADMIN) throw new Exception("Rol inválido para administrador");
    }

    public static String usernameValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Username inválido");
        return value;
    }

    public static String passwordValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Password inválido");
        return value;
    }
}
