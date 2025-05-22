package app.adapters.rest.request;

import app.domain.models.Person;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private String password;
    private Role role; 

    public UserAccount toUserAccount() {
        return UserAccount.builder()
                .document(document)
                .userName(username)
                .password(password)
                .role(role)
                .build();
    }

    public Person toPerson() {
        return Person.builder()
                .document(document)
                .name(name)
                .age(age)
                .role(role)
                .build();
    }

}
