package app.adapters.rest;

import app.adapters.rest.request.RegisterPetRequest;
import app.adapters.rest.utils.PetValidator;
import app.domain.models.Pet;
import app.domain.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    // Endpoint para registrar una mascota
    @PostMapping("/pet")
    public ResponseEntity<?> registerPet(@RequestBody RegisterPetRequest request) throws Exception {
        Pet pet = request.toPet();
        PetValidator.nameValidator(pet.getNamePet());
        PetValidator.ownersIdValidator(pet.getOwnersId());
        PetValidator.ageValidator(pet.getAge());
        PetValidator.speciesValidator(pet.getSpecies());
        PetValidator.raceValidator(pet.getRace());
        ownerService.registerPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mascota registrada exitosamente");
    }

    // Endpoint para buscar una mascota por ID
    @GetMapping("/pet/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id)  {
        Pet pet = ownerService.findPetById(id);
        return ResponseEntity.ok(pet);
    }

    // Endpoint para actualizar una mascota
    @PutMapping("/pet")
    public ResponseEntity<?> updatePet(@RequestBody RegisterPetRequest request) throws Exception {
        Pet pet = request.toPet();
        PetValidator.nameValidator(request.getName());
        PetValidator.ownersIdValidator(request.getOwnerDocument());
        PetValidator.ageValidator(request.getAge());
        PetValidator.speciesValidator(request.getSpecies());
        PetValidator.raceValidator(request.getRace());
        ownerService.updatePet(pet);
        return ResponseEntity.ok("Mascota actualizada exitosamente");
    }

    // Endpoint para listar todas las mascotas de un dueño
    @GetMapping("/{ownerDoc}/pets")
    public ResponseEntity<List<Pet>> listOwnerPets(@PathVariable Long ownerDoc) {
        List<Pet> pets = ownerService.listOwnerPets(ownerDoc);
        return ResponseEntity.ok(pets);
    }
}
