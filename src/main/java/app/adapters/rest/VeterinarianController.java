package app.adapters.rest;

import app.adapters.rest.request.RegisterMedicalHistoryRequest;
import app.adapters.rest.request.RegisterOrderRequest;
import app.adapters.rest.request.RegisterPetRequest;
import app.adapters.rest.utils.MedicalHistoryValidator;
import app.adapters.rest.utils.OrderValidator;
import app.adapters.rest.utils.PetValidator;
import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.domain.models.Pet;
import app.domain.services.VeterinarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

    // Registrar una orden médica
    @PostMapping("/order")
    public ResponseEntity<?> registerOrder(@RequestBody RegisterOrderRequest request) throws Exception {
        Order order = request.toOrder();
        OrderValidator.drugDosageValidator(request.getDrugDosage());
        OrderValidator.medicationValidator(request.getMedication());
        OrderValidator.petIdValidator(request.getPetId());
        OrderValidator.veterinarianIdValidator(request.getVeterinarianId());
        veterinarianService.registerOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Orden médica registrada exitosamente");
    }

    // Cancelar una orden médica
    @PostMapping("/order/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam long orderId, @RequestParam String reason) {
        veterinarianService.cancelOrder(orderId, reason);
        return ResponseEntity.ok("Orden médica cancelada exitosamente");
    }

    // Registrar una historia clínica
    @PostMapping("/medical-history")
    public ResponseEntity<?> registerMedicalHistory(@RequestBody RegisterMedicalHistoryRequest request) throws Exception {
        MedicalHistory mh = request.toMedicalHistory();
        MedicalHistoryValidator.validate(mh);
        veterinarianService.registerMedicalHistory(mh);
        return ResponseEntity.status(HttpStatus.CREATED).body("Historia clínica registrada exitosamente");
    }

    // Actualizar una historia clínica
    @PutMapping("/medical-history")
    public ResponseEntity<?> updateMedicalHistory(@RequestBody RegisterMedicalHistoryRequest request) throws Exception {
        MedicalHistory mh = request.toMedicalHistory();
        MedicalHistoryValidator.validate(mh);
        veterinarianService.updateMedicalHistory(mh);
        return ResponseEntity.ok("Historia clínica actualizada exitosamente");
    }

    // Registrar una mascota
    @PostMapping("/pet")
    public ResponseEntity<?> registerPet(@RequestBody RegisterPetRequest request) throws Exception {
        System.out.println("ENTRÓ AL CONTROLLER DE REGISTRO DE MASCOTA");
        Pet pet = request.toPet();
        PetValidator.nameValidator(request.getName());
        PetValidator.ownersIdValidator(request.getOwnerDocument());
        PetValidator.ageValidator(request.getAge());
        PetValidator.speciesValidator(request.getSpecies());
        PetValidator.raceValidator(request.getRace());
        veterinarianService.registerPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mascota registrada exitosamente");
    }

    // Actualizar una mascota
    @PutMapping("/pet")
    public ResponseEntity<?> updatePet(@RequestBody RegisterPetRequest request) throws Exception {
        Pet pet = request.toPet();
        PetValidator.nameValidator(request.getName());
        if (request.getOwnerDocument() != null) {
            PetValidator.ownersIdValidator(request.getOwnerDocument());
        }
        PetValidator.ageValidator(request.getAge());
        PetValidator.speciesValidator(request.getSpecies());
        PetValidator.raceValidator(request.getRace());
        veterinarianService.updatePet(pet);
        return ResponseEntity.ok("Mascota actualizada exitosamente");
    }

    // Buscar órdenes médicas por ID de mascota
    @GetMapping("/orders/pet/{petId}")
    public ResponseEntity<List<Order>> findPetOrders(@PathVariable Long petId) {
        List<Order> orders = veterinarianService.findPetOrders(petId);
        return ResponseEntity.ok(orders);
    }

    // Buscar historias clínicas por ID de mascota
    @GetMapping("/medical-histories/pet/{petId}")
    public ResponseEntity<List<MedicalHistory>> findAllMedicalHistoriesByPetId(@PathVariable Long petId) {
        List<MedicalHistory> histories = veterinarianService.findAllMedicalHistoriesByPetId(petId);
        return ResponseEntity.ok(histories);
    }
}
