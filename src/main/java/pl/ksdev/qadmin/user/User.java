package pl.ksdev.qadmin.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.ksdev.qadmin.shared.AbstractEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user") // ("user" is a reserved keyword in DB)
@Getter
@Setter
@UserDefinition
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    @Size(max = 1000)
    @Username
    private String username;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 1000)
    @Password
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "app_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Roles
    private Set<Role> roles = new HashSet<>();

    public User(@NonNull String username, @NonNull String password) {
        initUuid();
        this.username = username;
        this.password = password;
    }
}
