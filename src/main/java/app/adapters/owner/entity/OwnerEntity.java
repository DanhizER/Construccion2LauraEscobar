package app.adapters.owner.entity;

import app.domain.models.Person;
import app.domain.models.Pet;
import app.domain.types.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="owner")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerEntity {

    public OwnerEntity(Person person) {
        this.document = person.getDocument();
        this.name = person.getName();
        this.age = person.getAge();
        this.role = Role.USER;
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

    public Person toDomain(){
        return Person.builder()
                .name(this.name)
                .document(this.document)
                .age(this.age)
                .role(this.role)
                .build();
    }
}
