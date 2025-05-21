package app.domain.models;

import app.domain.types.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class Person {
	private Long document;
	private String name;
	private int age;
	private Role role;
	
	public Person(Long document, String name, int age, Role role) {
		this.document = document;
		this.name = name;
		this.age = age;
		this.role = role;
	}
}
