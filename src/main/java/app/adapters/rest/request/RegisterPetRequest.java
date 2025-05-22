package app.adapters.rest.request;

import app.domain.models.Pet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPetRequest {
    private Long petId;
    private String name;
    private String species;
    private String race;
    private Integer age;
    private Long ownerDocument;

    public Pet toPet() {
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
