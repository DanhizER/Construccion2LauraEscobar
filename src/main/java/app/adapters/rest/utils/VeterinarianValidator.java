package app.adapters.rest.utils;

import app.domain.models.Person;
import app.domain.types.Role;

public class VeterinarianValidator {
    

    public static Role roleValidator(Role value) throws Exception {
        if (value == null) throw new Exception("Rol inválido");
        return value;
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
