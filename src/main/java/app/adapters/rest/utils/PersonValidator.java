package app.adapters.rest.utils;

import org.springframework.stereotype.Component;

import app.domain.models.Person;

@Component
public class PersonValidator {

    // Validación de objeto completo (en grupo)
    public static void validate(Person person) throws Exception {
        if (person == null) throw new Exception("La persona no puede ser nula");
        documentValidator(person.getDocument());
        nameValidator(person.getName());
        ageValidator(person.getAge());
        roleValidator(person.getRole());
    }

    // Validadores individuales
    public static Long documentValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("Documento inválido");
        return value;
    }

    public static String nameValidator(String value) throws Exception {
        if (value == null || value.trim().isEmpty()) throw new Exception("Nombre inválido");
        return value;
    }

    public static Integer ageValidator(Integer value) throws Exception {
        if (value == null || value < 0) throw new Exception("Edad inválida");
        return value;
    }

    public static Object roleValidator(Object value) throws Exception {
        if (value == null) throw new Exception("Rol inválido");
        return value;
    }
}
