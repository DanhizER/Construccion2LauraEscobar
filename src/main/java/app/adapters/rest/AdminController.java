package app.adapters.rest;

import app.adapters.rest.request.RegisterUserRequest;
import app.adapters.rest.utils.PersonValidator;
import app.adapters.rest.utils.SellerValidator;
import app.adapters.rest.utils.VeterinarianValidator;
import app.domain.models.Person;
import app.domain.services.AdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;

    // Registrar vendedor
    @PostMapping("/register-seller")
    public ResponseEntity<?> registerSeller(@RequestBody RegisterUserRequest request) throws Exception {
        Person person = request.toPerson();
        PersonValidator.validate(person);
        SellerValidator.usernameValidator(request.getUsername());
        SellerValidator.passwordValidator(request.getPassword());
        adminServices.registerSeller(person, request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor registrado exitosamente");
    }

    @PostMapping("/register-veterinarian")
    public ResponseEntity<?> registerVeterinarian(@RequestBody RegisterUserRequest request) throws Exception {
        Person person = request.toPerson();
        PersonValidator.validate(person);
        VeterinarianValidator.usernameValidator(request.getUsername());
        VeterinarianValidator.passwordValidator(request.getPassword());
        adminServices.registerVeterinarian(person, request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("Veterinario registrado exitosamente");
    }
    // Eliminar usuario
    @DeleteMapping("/delete-user/{document}")
    public ResponseEntity<?> deleteUser(@PathVariable long document) {
        adminServices.deleteUser(document);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }
}
