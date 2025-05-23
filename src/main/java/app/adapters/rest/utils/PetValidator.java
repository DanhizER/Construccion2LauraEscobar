package app.adapters.rest.utils;

import app.domain.models.Pet;

public class PetValidator {


    // Validadores individuales
    public static String nameValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Nombre de mascota inválido");
        return value;
    }

    public static Long ownersIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de dueño inválido");
        return value;
    }

    public static Integer ageValidator(Integer value) throws Exception {
        if (value == null || value < 0) throw new Exception("Edad de mascota inválida");
        return value;
    }

    public static String speciesValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Especie inválida");
        return value;
    }

    public static String raceValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Raza inválida");
        return value;
    }
}
