package app.adapters.rest.request;

import app.domain.models.Person;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterOwnerRequest {
    private Long document;
    private String name;
    private Integer age;

    public Person toPerson() {
        return Person.builder()
                .document(document)
                .name(name)
                .age(age)
                .role(Role.USER) 
                .build();
    }
}