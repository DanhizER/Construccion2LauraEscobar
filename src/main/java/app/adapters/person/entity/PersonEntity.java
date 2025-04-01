package app.adapters.person.entity;

import app.domain.models.Person;
import app.domain.types.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="person")
@Setter
@Getter
@NoArgsConstructor
public class PersonEntity {
	public PersonEntity(Person person) {
		this.document = person.getDocument();
		this.name = person.getName();
		this.age = person.getAge();
		this.role = person.getRole();
	}
	
	@Id
	@Column(name="document")
	private long document;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private int age;
	@Column(name="role")
	private Role role;
}
