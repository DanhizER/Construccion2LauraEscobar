package app.adapters.useraccount.entity;

import app.domain.models.UserAccount;
import app.domain.types.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
        this.document = userAccount.getDocument();
        this.username = userAccount.getUserName();
        this.password = userAccount.getPassword();
        this.role = userAccount.getRole();
    }

    @Id
    @Column(name="document")
    private long document;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private Role role;
}
