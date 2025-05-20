package app.adapters.useraccount.entity;

import app.domain.models.UserAccount;
import app.domain.types.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name="user_account")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserAccountEntity {
    public UserAccountEntity(UserAccount userAccount) {
        this.document = userAccount.getDocument();
        this.username = userAccount.getUserName();
        this.password = userAccount.getPassword();
        this.role = userAccount.getRole();
    }

    @Id
    @Column(name="document")
    private Long document;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private Role role;

    public UserAccount toDomain() {
        return UserAccount.builder()
                .document(document)
                .userName(username)
                .password(password)
                .role(role)
                .build();
    }
}
