package app.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class Pet {
	private String namePet;
	private long ownersId;
	private int age;
	private long petId;
	private String species;
	private String race;
	private String characteristics;
	private double weight;
	
	public Pet(String namePet, long ownersId, int age, long petId, String species, String race, String characteristics,
			double weight) {;
		this.namePet = namePet;
		this.ownersId = ownersId;
		this.age = age;
		this.petId = petId;
		this.species = species;
		this.race = race;
		this.characteristics = characteristics;
		this.weight = weight;
	}
	
	
}
