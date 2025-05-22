package app.adapters.rest.utils;

import app.domain.models.Person;
import app.domain.types.Role;

public class SellerValidator {

    public static void validate(Person seller, String username, String password) throws Exception {
        if (seller == null) throw new Exception("El vendedor no puede ser nulo");
        PersonValidator.documentValidator(seller.getDocument());
        PersonValidator.nameValidator(seller.getName());
        PersonValidator.ageValidator(seller.getAge());
        PersonValidator.roleValidator(seller.getRole());
        usernameValidator(username);
        passwordValidator(password);
        if (seller.getRole() != Role.SELLER) throw new Exception("Rol inválido para vendedor");
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
