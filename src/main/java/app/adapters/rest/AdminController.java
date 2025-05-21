package app.adapters.rest;

import app.adapters.rest.request.RegisterUserRequest;
import app.domain.models.Person;
import app.domain.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @PostMapping("/register-seller")
    public void registerSeller(@RequestBody RegisterUserRequest request) {
        Person person = request.toPerson();
        adminServices.registerSeller(person, request.getUserName(), request.getPassword());
    }

    @PostMapping("/register-veterinarian")
    public void registerVeterinarian(@RequestBody RegisterUserRequest request) {
        Person person = request.toPerson();
        adminServices.registerVeterinarian(person, request.getUserName(), request.getPassword());
    }

    @DeleteMapping("/delete-user/{document}")
    public void deleteUser(@PathVariable Long document) {
        adminServices.deleteUser(document);
    }
}

