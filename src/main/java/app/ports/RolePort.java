package app.ports;

import app.domain.types.Role;

public interface RolePort {
	Role getRoleByName(String roleName);
}
