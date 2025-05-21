package app.adapters.rest;

import app.adapters.rest.request.RegisterUserRequest;
import app.adapters.rest.response.UserResponse;
import app.domain.models.UserAccount;
import app.domain.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterUserRequest request) throws Exception {
        UserAccount user = new UserAccount();
        user.setDocument(request.getDocument());
        user.setUserName(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setName(request.getName());
        user.setAge(request.getAge());
        userAccountService.registerUser(user);
        return UserResponse.fromUserAccount(user);
    }

    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userAccountService.getUserByUserame(username);
    }

    
}
