package app.adapters.rest.utils;

import org.springframework.stereotype.Component;
import app.Exceptions.InputsException;

@Component
public class SimpleValidator {
    public String stringValidator(String value, String element)throws Exception{
        if(value==null || value.trim().isEmpty()){
            throw new InputsException(element + "no tiene un valor válido");
        }
        return value.trim();
    }
}
