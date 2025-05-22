package app.adapters.rest.response;

import app.domain.models.Person;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianResponse {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private Role role;

    public static VeterinarianResponse fromPerson(Person person, String username, String specialty) {
        VeterinarianResponse response = new VeterinarianResponse();
        response.setDocument(person.getDocument());
        response.setName(person.getName());
        response.setAge(person.getAge());
        response.setUsername(username);
        response.setRole(person.getRole());
        return response;
    }
}
