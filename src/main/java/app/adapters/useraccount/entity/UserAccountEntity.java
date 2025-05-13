package app.adapters.useraccount.entity;

import app.adapters.person.entity.PersonEntity;
import app.domain.models.UserAccount;
import app.domain.types.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_account")
@Setter
@Getter
@NoArgsConstructor
public class UserAccountEntity {
    public UserAccountEntity(UserAccount userAccount) {
        this.person = new PersonEntity(userAccount);
        this.userId = userAccount.getUserId();
        this.username = userAccount.getUserName();
        this.password = userAccount.getPassword();
        this.role = userAccount.getRole();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private long userId;
    @OneToOne
    @JoinColumn(name = "document", referencedColumnName = "document", insertable = false, updatable = false)
    private PersonEntity person;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private Role role;


}
