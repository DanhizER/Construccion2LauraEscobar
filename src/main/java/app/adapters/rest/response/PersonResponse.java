package app.adapters.rest.response;

import app.domain.models.Person;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponse {
    private Long document;
    private String name;
    private Integer age;
    private Role role;

    public static PersonResponse fromPerson(Person person) {
        PersonResponse response = new PersonResponse();
        response.setDocument(person.getDocument());
        response.setName(person.getName());
        response.setAge(person.getAge());
        response.setRole(person.getRole());
        return response;
    }
}