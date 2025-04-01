package app.domain.types;


public enum Role {
	ADMIN("Administrador"),
    VETERINARIAN("Médico Veterinario"),
    SELLER("Vendedor"),
	USER("Usuario");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

   // @Override
    public String getDisplayName() {
        return displayName;
    }
}
