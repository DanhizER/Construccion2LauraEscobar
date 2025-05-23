package app.console;

import app.adapters.rest.utils.InvoiceValidator;
import app.adapters.rest.utils.MedicalHistoryValidator;
import app.adapters.rest.utils.OrderValidator;
import app.adapters.rest.utils.PersonValidator;
import app.adapters.rest.utils.PetValidator;
import app.adapters.rest.utils.SellerValidator;
import app.adapters.rest.utils.Utils;
import app.adapters.rest.utils.VeterinarianValidator;
import app.domain.models.Invoice;
import app.domain.models.MedicalHistory;
import app.domain.models.Order;
import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.models.UserAccount;
import app.domain.services.AdminServices;
import app.domain.services.OwnerService;
import app.domain.services.SellerService;
import app.domain.services.UserAccountService;
import app.domain.services.VeterinarianService;
import app.domain.types.Role;

public class MenuConsola {

    private final UserAccountService userAccountService;
    private final AdminServices adminServices;
    private final VeterinarianService veterinarianService;
    private final SellerService sellerService;
    private final OwnerService ownerService;

    public MenuConsola(UserAccountService userAccountService, AdminServices adminServices, VeterinarianService veterinarianService, SellerService sellerService, OwnerService ownerService) {
        this.userAccountService = userAccountService;
        this.adminServices = adminServices;
        this.veterinarianService = veterinarianService;
        this.sellerService = sellerService;
        this.ownerService = ownerService;
    }

    public void mostrarMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse como usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int mainOption = Utils.getReader().nextInt();
            Utils.getReader().nextLine();

            switch (mainOption) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    registrarOwnerPublico();
                    break;
                case 3:
                    exit = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public void loginMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("===== INICIO DE SESIÓN =====");
            System.out.print("Usuario: ");
            String username = Utils.getReader().nextLine();
            System.out.print("Contraseña: ");
            String password = Utils.getReader().nextLine();

            UserAccount loginRequest = UserAccount.builder()
                .userName(username)
                .password(password)
                .build();
            UserAccount user = null;
            try {
                user = userAccountService.login(loginRequest);
            } catch (Exception e) {
                System.out.println("Error al intentar iniciar sesión: " + e.getMessage());
            }
            
            if (user == null) {
                System.out.println("Usuario o contraseña incorrectos.\n");
            } else {
                switch (user.getRole().name()) {
                    case "ADMIN":
                        menuAdmin();
                        break;
                    case "VETERINARIO":
                        menuVeterinarian();
                        break;
                    case "SELLER":
                        menuSeller();
                        break;
                    case "USER":
                        menuUser();
                        break;
                    default:
                        System.out.println("Rol no reconocido.");
                }
                System.out.println("¿Desea cerrar sesión? (s/n): ");
                String opcion = Utils.getReader().nextLine();
                if (opcion.equalsIgnoreCase("n")) {
                    exit = true;
                }
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private void menuAdmin() {
        boolean back = false;
    while (!back) {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. Registrar vendedor");
        System.out.println("2. Registrar veterinario");
        System.out.println("3. Eliminar usuario");
        System.out.println("4. Cerrar sesión");
        System.out.print("Seleccione una opción: ");
        int option = Utils.getReader().nextInt();
        Utils.getReader().nextLine(); // Clear buffer

        switch (option) {
            case 1:
                System.out.println("=== Registro de Vendedor ===");
                System.out.print("Documento: ");
                long sellerDoc = Utils.getReader().nextLong();
                Utils.getReader().nextLine();
                System.out.print("Nombre: ");
                String sellerName = Utils.getReader().nextLine();
                System.out.print("Edad: ");
                int sellerAge = Utils.getReader().nextInt();
                Utils.getReader().nextLine();
                System.out.print("Username: ");
                String sellerUsername = Utils.getReader().nextLine();
                System.out.print("Password: ");
                String sellerPassword = Utils.getReader().nextLine();
                try {
                    Person seller = new Person(sellerDoc, sellerName, sellerAge, Role.SELLER);
                    PersonValidator.validate(seller);
                    SellerValidator.usernameValidator(sellerUsername);
                    SellerValidator.passwordValidator(sellerPassword);
                    adminServices.registerSeller(seller, sellerUsername, sellerPassword);
                    System.out.println("Vendedor Registrado satisfactoriamente!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 2:
                System.out.println("=== Registro de Veterinario ===");
                System.out.print("Documento: ");
                Long vetDoc = Utils.getReader().nextLong();
                Utils.getReader().nextLine();
                System.out.print("Nombre: ");
                String vetName = Utils.getReader().nextLine();
                System.out.print("Edad: ");
                int vetAge = Utils.getReader().nextInt();
                Utils.getReader().nextLine();
                System.out.print("Username: ");
                String vetUsername = Utils.getReader().nextLine();
                System.out.print("Password: ");
                String vetPassword = Utils.getReader().nextLine();
                try {
                    Person vet = new Person(vetDoc, vetName, vetAge, Role.VETERINARIAN);
                    PersonValidator.validate(vet);
                    VeterinarianValidator.usernameValidator(vetUsername);
                    VeterinarianValidator.passwordValidator(vetPassword);
                    adminServices.registerVeterinarian(vet, vetUsername, vetPassword);
                    System.out.println("veterinario agregado exitosamente!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 3:
                System.out.println("=== Eliminar usuario ===");
                System.out.print("Documento: ");
                long userDoc = Utils.getReader().nextLong();
                Utils.getReader().nextLine();
                try {
                    adminServices.deleteUser(userDoc);
                    System.out.println("Usuario eliminado satisfactoriamente!");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 4:
                userAccountService.logout();
                System.out.println("Cerrando sesión...");
                back = true;
                break;
            default:
                System.out.println("Opción invalida");
        }
    }
    }

    private void menuVeterinarian() {
        boolean back = false;
    while (!back) {
        System.out.println("\n--- VETERINARIO MENU ---");
        System.out.println("1. Registrar orden medica");
        System.out.println("2. Cancelar orden medica");
        System.out.println("3. Registrar historia medica");
        System.out.println("4. Actualizar historia medica");
        System.out.println("5. Registrar mascota");
        System.out.println("6. Actualizar mascota");
        System.out.println("7. Buscar ordenes por mascota");
        System.out.println("8. Buscar historias medicas por mascota");
        System.out.println("9. Cerrar sesión");
        System.out.print("Seleccione una opción: ");
        int option = Utils.getReader().nextInt();
        Utils.getReader().nextLine();

        try {
            switch (option) {
                case 1:
                    System.out.println("=== Registrar orden medica ===");
                    System.out.print("Order ID: ");
                    Long orderId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Pet ID: ");
                    Long petId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Veterinarian Document: ");
                    Long vetDoc = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Medicamento: ");
                    String medication = Utils.getReader().nextLine();
                    System.out.print("Drug Dosage: ");
                    String drugDosage = Utils.getReader().nextLine();
                    // Build Order object
                    Order order = Order.builder()
                        .orderId(orderId)
                        .pet(Pet.builder().petId(petId).build())
                        .veterinarian(UserAccount.builder().document(vetDoc).build())
                        .medication(medication)
                        .drugDosage(drugDosage)
                        .build();
                    OrderValidator.drugDosageValidator(order.getDrugDosage());
                    OrderValidator.medicationValidator(order.getMedication());
                    OrderValidator.orderIdValidator(order.getOrderId());
                    OrderValidator.petIdValidator(order.getPet().getPetId());
                    OrderValidator.veterinarianIdValidator(order.getVeterinarian().getDocument());
                    veterinarianService.registerOrder(order);
                    System.out.println("Orden medica creada satisfactoriamente!");
                    break;
                case 2:
                    System.out.println("=== Cancelar orden medica ===");
                    System.out.print("Order ID: ");
                    long cancelOrderId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Razón: ");
                    String reason = Utils.getReader().nextLine();
                    veterinarianService.cancelOrder(cancelOrderId, reason);
                    System.out.println("Orden medica cancelada satisfactoriamente!");
                    break;
                case 3:
                    System.out.println("=== Register Medical History ===");
                    // Request medical history data
                    System.out.print("Medical History ID: ");
                    Long mhId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Pet ID: ");
                    Long mhPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Veterinarian Document: ");
                    Long mhVetDoc = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Reason for Consultation: ");
                    String reasonConsultation = Utils.getReader().nextLine();
                    System.out.print("Diagnosis: ");
                    String diagnosis = Utils.getReader().nextLine();
                    System.out.print("Procedure: ");
                    String procedure = Utils.getReader().nextLine();
                    // Build MedicalHistory object
                    MedicalHistory mh = MedicalHistory.builder()
                        .medicalHistoryID(mhId)
                        .pet(Pet.builder().petId(mhPetId).build())
                        .veterinarian(UserAccount.builder().document(mhVetDoc).build())
                        .reasonConsultation(reasonConsultation)
                        .diagnosis(diagnosis)
                        .procedure(procedure)
                        .build();
                    MedicalHistoryValidator.validate(mh);
                    veterinarianService.registerMedicalHistory(mh);
                    System.out.println("Historia medica creada exitosamente!");
                    break;
                case 4:
                    System.out.println("=== Actualizar historia medica ===");
                    // Request updated medical history data (similar to register)
                    System.out.print("Medical History ID: ");
                    Long updMhId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Pet ID: ");
                    Long updPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Veterinarian Document: ");
                    Long updVetDoc = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("ReRazón de la consulta: ");
                    String updReasonConsultation = Utils.getReader().nextLine();
                    System.out.print("Diagnositico: ");
                    String updDiagnosis = Utils.getReader().nextLine();
                    System.out.print("Procedimiento: ");
                    String updProcedure = Utils.getReader().nextLine();
                    MedicalHistory updMh = MedicalHistory.builder()
                        .medicalHistoryID(updMhId)
                        .pet(Pet.builder().petId(updPetId).build())
                        .veterinarian(UserAccount.builder().document(updVetDoc).build())
                        .reasonConsultation(updReasonConsultation)
                        .diagnosis(updDiagnosis)
                        .procedure(updProcedure)
                        .build();
                    MedicalHistoryValidator.validate(updMh);
                    veterinarianService.updateMedicalHistory(updMh);
                    System.out.println("Historia medica actualizada exitosamente!");
                    break;
                case 5:
                    System.out.println("=== Registrar mascota ===");
                    System.out.print("Pet ID: ");
                    Long regPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Documento dueño: ");
                    Long ownerId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Nombre: ");
                    String petName = Utils.getReader().nextLine();
                    System.out.print("Edad: ");
                    int petAge = Utils.getReader().nextInt();
                    Utils.getReader().nextLine();
                    System.out.print("Especie: ");
                    String species = Utils.getReader().nextLine();
                    System.out.print("Raza: ");
                    String race = Utils.getReader().nextLine();
                    System.out.print("Peso: ");
                    double weight = Utils.getReader().nextDouble();
                    Utils.getReader().nextLine();
                    Pet regPet = Pet.builder()
                        .petId(regPetId)
                        .ownersId(ownerId)
                        .namePet(petName)
                        .age(petAge)
                        .species(species)
                        .race(race)
                        .weight(weight)
                        .build();
                    PetValidator.nameValidator(regPet.getNamePet());
                    PetValidator.ownersIdValidator(regPet.getOwnersId());
                    PetValidator.ageValidator(regPet.getAge());
                    PetValidator.speciesValidator(regPet.getSpecies());
                    PetValidator.raceValidator(regPet.getRace());  
                    veterinarianService.registerPet(regPet);
                    System.out.println("Mascota registrada exitosamente!");
                    break;
                case 6:
                    System.out.println("=== Actualizar mascota ===");
                    System.out.print("Pet ID: ");
                    Long updPetId2 = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Documento dueño: ");
                    Long updOwnerId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Nombre: ");
                    String updPetName = Utils.getReader().nextLine();
                    System.out.print("Edad: ");
                    int updPetAge = Utils.getReader().nextInt();
                    Utils.getReader().nextLine();
                    System.out.print("Especie: ");
                    String updSpecies = Utils.getReader().nextLine();
                    System.out.print("Raza: ");
                    String updRace = Utils.getReader().nextLine();
                    System.out.print("Peso: ");
                    double updWeight = Utils.getReader().nextDouble();
                    Utils.getReader().nextLine();
                    Pet updPet = Pet.builder()
                        .petId(updPetId2)
                        .ownersId(updOwnerId)
                        .namePet(updPetName)
                        .age(updPetAge)
                        .species(updSpecies)
                        .race(updRace)
                        .weight(updWeight)
                        .build();
                    PetValidator.nameValidator(updPet.getNamePet());
                    PetValidator.ownersIdValidator(updPet.getOwnersId());
                    PetValidator.ageValidator(updPet.getAge());
                    PetValidator.speciesValidator(updPet.getSpecies());
                    PetValidator.raceValidator(updPet.getRace());  
                    veterinarianService.registerPet(updPet);
                    veterinarianService.updatePet(updPet);
                    System.out.println("Mascota actualizada exitosamente!");
                    break;
                case 7:
                    System.out.println("=== Buscar orden medica por ID de la mascota ===");
                    System.out.print("Pet ID: ");
                    Long searchPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.println("Orden par la mascota " + searchPetId + ":");
                    veterinarianService.findPetOrders(searchPetId)
                        .forEach(o -> System.out.println(o.toString()));
                    break;
                case 8:
                    System.out.println("=== Buscar historia medica por ID de mascota ===");
                    System.out.print("Pet ID: ");
                    Long searchMhPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.println("Historia medica para la moscota " + searchMhPetId + ":");
                    veterinarianService.findAllMedicalHistoriesByPetId(searchMhPetId)
                        .forEach(mh2 -> System.out.println(mh2.toString()));
                    break;
                case 9:
                    userAccountService.logout();
                    System.out.println("Cerrando sesión...");
                    back = true;
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    }

    private void menuSeller() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- VENDEDOR MENU ---");
            System.out.println("1. Registrar factura");
            System.out.println("2. Listar todas las facturas");
            System.out.println("3. Listar facturas por documento del dueño");
            System.out.println("4. Listar todas las ordenes medicas");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int option = Utils.getReader().nextInt();
            Utils.getReader().nextLine();
        try {
            switch (option) {
                case 1:
                    System.out.println("=== Registrar Factura) ===");
                    System.out.print("Factura ID: ");
                    Long invoiceId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Orden ID (o 0 si no aplica): ");
                    Long orderId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Documento del comprador: ");
                    Long ownerDoc = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Total: ");
                    double total = Utils.getReader().nextDouble();
                    Utils.getReader().nextLine();
                    // Build Invoice object
                    Invoice invoice = Invoice.builder()
                        .invoiceId(invoiceId)
                        .orderId(orderId != 0 ? orderId : null)
                        .ownersId(ownerDoc)
                        .value(total)
                        .build();
                    InvoiceValidator.invoiceIdValidator(invoice.getInvoiceId());
                    InvoiceValidator.ownerDocumentValidator(invoice.getOwnersId());
                    InvoiceValidator.orderIdValidator(invoice.getOrderId());
                    InvoiceValidator.totalValidator(invoice.getValue());
                    InvoiceValidator.productNameValidator("Producto"); // Placeholder
                    InvoiceValidator.productQuantityValidator(1); // Placeholder
                    sellerService.SellProduct(invoice);
                    System.out.println("Factura registrada satisfactoriamente!");
                    break;
                case 2:
                    System.out.println("=== Listar todas las facturas) ===");
                    sellerService.listInvoices().forEach(inv -> System.out.println(inv.toString()));
                    break;
                case 3:
                    System.out.println("=== Listar facturas por documento del comprador ===");
                    System.out.print("Documento del comprador: ");
                    Long searchOwnerDoc = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    sellerService.listInvoicesByOwnerDoc(searchOwnerDoc)
                        .forEach(inv -> System.out.println(inv.toString()));
                    break;
                case 4:
                    System.out.println("=== Listar todas las ordenes medicas ===");
                    sellerService.listOrders().forEach(order -> System.out.println(order.toString()));
                    break;
                case 5:
                    userAccountService.logout();
                    System.out.println("Cerrando sesión...");
                    back = true;
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        }
    }

    private void menuUser() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MENU DE USUARIO ---");
            System.out.println("1. Registrar mascota");
            System.out.println("2. Actualizar mascota");
            System.out.println("3. Listar mis mascotas");
            System.out.println("4. Buscar mascota por ID");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int option = Utils.getReader().nextInt();
            Utils.getReader().nextLine();

        try {
            switch (option) {
                case 1:
                    System.out.println("=== Registrar mascota ===");
                    System.out.print("Pet ID: ");
                    Long petId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Nombre: ");
                    String name = Utils.getReader().nextLine();
                    System.out.print("Edad: ");
                    int age = Utils.getReader().nextInt();
                    Utils.getReader().nextLine();
                    System.out.print("Especie: ");
                    String species = Utils.getReader().nextLine();
                    System.out.print("Raza: ");
                    String race = Utils.getReader().nextLine();
                    System.out.print("Peso: ");
                    double weight = Utils.getReader().nextDouble();
                    Utils.getReader().nextLine();

                    Long ownerDoc = userAccountService.getCurrentUser().getDocument();
                    Pet pet = Pet.builder()
                        .petId(petId)
                        .ownersId(ownerDoc)
                        .namePet(name)
                        .age(age)
                        .species(species)
                        .race(race)
                        .weight(weight)
                        .build();
                    PetValidator.nameValidator(pet.getNamePet());
                    PetValidator.ownersIdValidator(pet.getOwnersId());
                    PetValidator.ageValidator(pet.getAge());
                    PetValidator.speciesValidator(pet.getSpecies());
                    PetValidator.raceValidator(pet.getRace());
                    ownerService.registerPet(pet);
                    System.out.println("Mascota registrada satisfactoriamente!");
                    break;
                case 2:
                    System.out.println("=== Actualizar Mascota ===");
                    System.out.print("Pet ID: ");
                    Long updPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    System.out.print("Nombre: ");
                    String updName = Utils.getReader().nextLine();
                    System.out.print("Edad: ");
                    int updAge = Utils.getReader().nextInt();
                    Utils.getReader().nextLine();
                    System.out.print("Especies: ");
                    String updSpecies = Utils.getReader().nextLine();
                    System.out.print("Raze: ");
                    String updRace = Utils.getReader().nextLine();
                    System.out.print("Peso: ");
                    double updWeight = Utils.getReader().nextDouble();
                    Utils.getReader().nextLine();

                    Long updOwnerDoc = userAccountService.getCurrentUser().getDocument();
                    Pet updPet = Pet.builder()
                        .petId(updPetId)
                        .ownersId(updOwnerDoc)
                        .namePet(updName)
                        .age(updAge)
                        .species(updSpecies)
                        .race(updRace)
                        .weight(updWeight)
                        .build();
                    PetValidator.nameValidator(updPet.getNamePet());
                    PetValidator.ownersIdValidator(updPet.getOwnersId());
                    PetValidator.ageValidator(updPet.getAge());
                    PetValidator.speciesValidator(updPet.getSpecies());
                    PetValidator.raceValidator(updPet.getRace());
                    ownerService.updatePet(updPet);
                    System.out.println("Mascota actualizada satisfactoriamente!");
                    break;
                case 3:
                    System.out.println("=== Mis mascotas ===");
                    Long myDoc = userAccountService.getCurrentUser().getDocument();
                    ownerService.listOwnerPets(myDoc)
                        .forEach(p -> System.out.println(p.toString()));
                    break;
                case 4:
                    System.out.println("=== Buscar mascota por ID ===");
                    System.out.print("Pet ID: ");
                    Long searchPetId = Utils.getReader().nextLong();
                    Utils.getReader().nextLine();
                    Pet foundPet = ownerService.findPetById(searchPetId);
                    System.out.println(foundPet != null ? foundPet.toString() : "Pet not found.");
                    break;
                case 5:
                    userAccountService.logout();
                    System.out.println("Cerrando sesión...");
                    back = true;
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        }    
    }

    private void registrarOwnerPublico() {
        System.out.println("=== Registro de Dueño ===");
        System.out.print("Documento: ");
        long ownerDoc = Utils.getReader().nextLong();
        Utils.getReader().nextLine();
        System.out.print("Nombre completo: ");
        String ownerName = Utils.getReader().nextLine();
        System.out.print("Edad: ");
        int ownerAge = Utils.getReader().nextInt();
        Utils.getReader().nextLine();

        // Generar username automático (ejemplo: JuanPerez15)
        String[] nameParts = ownerName.trim().split("\\s+");
        String nombre = nameParts.length > 0 ? capitalize(nameParts[0]) : "";
        String apellido = nameParts.length > 1 ? capitalize(nameParts[1]) : "";
        String docStr = String.valueOf(ownerDoc);
        String lastTwo = docStr.length() >= 2 ? docStr.substring(docStr.length() - 2) : docStr;
        String username = nombre + apellido + lastTwo;

        // Permitir que el owner elija su contraseña
        System.out.print("Escriba su contraseña: ");
        String password = Utils.getReader().nextLine();

        try {
            Person owner = new Person(ownerDoc, ownerName, ownerAge, Role.USER);
            PersonValidator.validate(owner);
            // Registrar tanto el Person como el UserAccount
            ownerService.registerOwner(owner, username, password);
            System.out.println("Dueño registrado exitosamente.");
            System.out.println("Su usuario es: " + username + " y su contraseña es: " + password);
            System.out.println("¡Ahora puede iniciar sesión!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String capitalize(String str) {
    if (str == null || str.isEmpty()) return "";
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
}
}
