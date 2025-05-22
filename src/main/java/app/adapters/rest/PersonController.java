package app.adapters.rest;

import app.adapters.rest.request.RegisterPersonRequest;
import app.adapters.rest.utils.PersonValidator;
import app.domain.models.Person;
import app.domain.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    //Endpoint Crear persona
    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterPersonRequest request) throws Exception {
        try {
            Person person = request.toPerson();
            PersonValidator.validate(person);
            personService.savePerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body("Persona registrada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Endpoint obtener persona por documento
    @GetMapping("/{document}")
    public ResponseEntity<?> get(@PathVariable Long document) {
        try {
            Person person = personService.findByDocument(document);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Endpoint eliminar persona
    @DeleteMapping("/{document}")
    public ResponseEntity<?> delete(@PathVariable Long document) {
        try {
            personService.deleteByDocument(document);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
