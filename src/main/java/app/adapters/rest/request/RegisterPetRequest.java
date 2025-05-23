package app.adapters.rest.request;

import app.domain.models.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPetRequest {
    private Long petId;
    private String name;
    private String species;
    private String race;
    private Integer age;
    private Long ownerDocument;

    public Pet toPet() {
         System.out.println("ownerDocument recibido en request: " + ownerDocument);
        return Pet.builder()
                .petId(petId)
                .namePet(name)
                .species(species)
                .race(race)
                .age(age)
                .ownersId(ownerDocument)
                .build();
    }
}
