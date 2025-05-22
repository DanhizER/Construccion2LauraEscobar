package app.adapters.rest.utils;

import app.domain.models.Person;
import app.domain.types.Role;

public class VeterinarianValidator {

    public static void validate(Person vet, String username, String password) throws Exception {
        if (vet == null) throw new Exception("El veterinario no puede ser nulo");
        PersonValidator.documentValidator(vet.getDocument());
        PersonValidator.nameValidator(vet.getName());
        PersonValidator.ageValidator(vet.getAge());
        PersonValidator.roleValidator(vet.getRole());
        usernameValidator(username);
        passwordValidator(password);
        if (vet.getRole() != Role.VETERINARIAN) throw new Exception("Rol inválido para veterinario");
    }

    public static String usernameValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Username inválido");
        return value;
    }

    public static String passwordValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Contraseña inválida");
        return value;
    }
}
