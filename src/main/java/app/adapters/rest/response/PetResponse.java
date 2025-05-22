package app.adapters.rest.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private Long ownerDocument;

    public static PetResponse fromPet(app.domain.models.Pet pet) {
        PetResponse response = new PetResponse();
        response.setId(pet.getPetId());
        response.setName(pet.getNamePet());
        response.setSpecies(pet.getSpecies());
        response.setBreed(pet.getRace());
        response.setAge(pet.getAge());
        response.setOwnerDocument(pet.getOwnersId());
        return response;
    }
}
