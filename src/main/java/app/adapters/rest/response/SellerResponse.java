package app.adapters.rest.response;

import app.domain.models.Person;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerResponse {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private Role role;

    public static SellerResponse fromPerson(Person person, String username) {
        SellerResponse response = new SellerResponse();
        response.setDocument(person.getDocument());
        response.setName(person.getName());
        response.setAge(person.getAge());
        response.setUsername(username);
        response.setRole(person.getRole());
        return response;
    }
}