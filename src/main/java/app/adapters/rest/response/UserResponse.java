package app.adapters.rest.response;

import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private Role role;

    public static UserResponse fromUserAccount(app.domain.models.UserAccount userAccount) {
    UserResponse response = new UserResponse();
    response.setDocument(userAccount.getDocument());
    response.setName(userAccount.getName());
    response.setAge(userAccount.getAge());
    response.setUsername(userAccount.getUserName()); 
    response.setRole(userAccount.getRole());
    return response;
    }
}
