package pl.ksdev.qadmin.user;

import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.ksdev.qadmin.shared.AbstractEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 1000)
    @RolesValue
    private String name;

    public Role(@NonNull String name) {
        initUuid();
        this.name = name;
    }
}
