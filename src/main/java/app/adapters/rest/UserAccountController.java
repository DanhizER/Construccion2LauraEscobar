package app.adapters.rest;

import app.adapters.rest.request.RegisterUserRequest;
import app.adapters.rest.request.LoginRequest;
import app.adapters.rest.response.UserResponse;
import app.adapters.rest.utils.UserValidator;
import app.domain.models.UserAccount;
import app.domain.services.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    //Endpont Crear usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        try {
            UserAccount user = request.toUserAccount();
            UserValidator.validate(user);
            userAccountService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Endpoint iniciar sesion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UserAccount user = new UserAccount();
            UserValidator.validate(user);
            user.setUserName(request.getUsername());
            user.setPassword(request.getPassword());
            UserAccount logged = userAccountService.login(user);
            return ResponseEntity.ok("Login exitoso: " + logged.getUserName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Endpoint obtener usuario por documento
    @GetMapping("/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        try {
            UserAccount user = userAccountService.findByUserName(userName);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Endpoint obtener todos los usuarios
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<UserAccount> users = userAccountService.findAllUsers(); 
            List<UserResponse> response = users.stream()
                    .map(UserResponse::fromUserAccount)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Endpoint eliminar usuario
    @DeleteMapping("/{document}")
    public ResponseEntity<?> delete(@PathVariable Long document) {
        try {
            userAccountService.deleteUser(document);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Endpoint cambiar contraseña
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            UserValidator.passwordValidator(newPassword);
            userAccountService.changePassword(username, oldPassword, newPassword);
            return ResponseEntity.ok("Contraseña cambiada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
