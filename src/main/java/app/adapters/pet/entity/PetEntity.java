package app.adapters.pet.entity;

import jakarta.persistence.*;
import app.adapters.owner.entity.OwnerEntity;
import app.domain.models.Pet;
import lombok.*;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petid")
    private Long petId;
    @Column(name = "namePet")
    private String namePet;   
    @ManyToOne
    @JoinColumn(name = "ownersId", referencedColumnName = "document")
	private OwnerEntity ownersId;
    @Column(name = "age")
	private int age;
    @Column(name = "species")
	private String species;
    @Column(name = "race")
	private String race;
    @Column(name = "characteristics")
	private String characteristics;
    @Column(name = "weight")
	private double weight;

    public PetEntity(Pet pet) {
        this.petId = pet.getPetId();
        this.namePet = pet.getNamePet();
        this.age = pet.getAge();
        this.species = pet.getSpecies();
        this.race = pet.getRace();
        this.characteristics = pet.getCharacteristics();
        this.weight = pet.getWeight();
    }

    public Pet toDomain() {
        return Pet.builder()
                .petId(this.petId)
                .namePet(this.namePet)
                .ownersId(this.ownersId != null ? this.ownersId.getDocument() : null)
                .age(this.age)
                .species(this.species)
                .race(this.race)
                .characteristics(this.characteristics)
                .weight(this.weight)
                .build();
    }

    
}
