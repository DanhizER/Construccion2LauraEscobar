package app.adapters.person.entity;

import app.domain.models.Person;
import app.domain.types.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="person")
@Setter
@Getter
@NoArgsConstructor
public class PersonEntity {	
	@Id
	@Column(name="document")
	private long document;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	@Column(name="role")
	private Role role;

	public PersonEntity(Person person) {
		this.document = person.getDocument();
		this.name = person.getName();
		this.age = person.getAge();
		this.role = person.getRole();
	}
}
