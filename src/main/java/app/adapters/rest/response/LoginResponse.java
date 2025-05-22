package app.adapters.rest.response;

import app.domain.types.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String username;
    private Role role;
}