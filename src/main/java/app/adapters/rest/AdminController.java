package app.adapters.rest;

import java.util.ArrayList;
import java.util.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import app.adapters.person.PersonAdapter;
import app.domain.services.AdminServices;

@RestController
public class AdminController {

    @Autowired
    private AdminServices adminServices;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private UserValidator UserValidator;

    //Crear Usuario Veterinario (Post)
    @PostMapping("/veterinarian")
    Public ResponseEntity createVeterinarian(@RequestBody UserRequest request){
        try{
            personValidator.validate(request);
            user.validate(request);

            UserAccount user = request.toMdodel();
            adminServices.registerVeterinarian(user,user);;
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
    
    //Crear Usuario Vendedor (Post)
    @PostMapping("/seller")
    Public ResponseEntity createSeller(@RequestBody UserRequest request){
        try{
            personValidator.validate(request);
            user.validate(request);

            UserAccount user = request.toMdodel();
            adminServices.registerSeller(user,user);

            return ResponseEntity.ok("Vendedor registrado exitosamente");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }


    //Eliminar Usuario ()
    public ResponseEntity deleteUser(@PathVariable Long document){
        try{
            adminServices.deleteUser(document);
            return ResponseEntity.ok("Usuario elimiado exitosamente");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }




}