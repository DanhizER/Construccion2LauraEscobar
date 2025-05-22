package app.adapters.rest.request;


import app.domain.models.UserAccount;
import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterSellerRequest {
    private Long document;
    private String name;
    private Integer age;
    private String username;
    private String password;

    public UserAccount toSeller() {
        return UserAccount.builder()
                .document(document)
                .name(name)
                .age(age)
                .userName(username)
                .password(password)
                .role(Role.SELLER)
                .build();
    }
}