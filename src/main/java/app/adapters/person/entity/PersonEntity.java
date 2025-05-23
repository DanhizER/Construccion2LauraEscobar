package app.adapters.person.entity;

import app.domain.models.Person;
import app.domain.types.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="person")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {	
	@Id
	@Column(name="document")
	private long document;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role;

	public PersonEntity(Person person) {
		this.document = person.getDocument();
		this.name = person.getName();
		this.age = person.getAge();
		this.role = person.getRole();
	}

	public static PersonEntity fromDomain(Person person) {
		return PersonEntity.builder()
				.document(person.getDocument())
				.name(person.getName())
				.age(person.getAge())
				.role(person.getRole())
				.build();
	}
}
