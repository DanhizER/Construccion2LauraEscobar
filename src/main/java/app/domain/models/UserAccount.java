package app.domain.models;

import app.domain.types.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserAccount extends Person {
	private Long userId;
	private String userName;
	private String password;
	
	public UserAccount(long document, String nombre, int age, Role role, String userName, String password,Long userId) {
		super(document, nombre, age, role);
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}		
	
}
