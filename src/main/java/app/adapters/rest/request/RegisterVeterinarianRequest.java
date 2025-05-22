package app.adapters.rest.request;

import app.domain.models.Person;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterVeterinarianRequest {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private String password;
    private Role role;

    public Person toPerson() {
        return Person.builder()
                .document(document)
                .name(name)
                .age(age)
                .role(Role.VETERINARIAN)
                .build();
    }
}
