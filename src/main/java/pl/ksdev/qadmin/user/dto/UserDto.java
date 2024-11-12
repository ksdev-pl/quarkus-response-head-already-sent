package pl.ksdev.qadmin.user.dto;

import io.quarkus.qute.TemplateData;
import pl.ksdev.qadmin.user.User;

@TemplateData
public record UserDto(
    Long id,
    String username,
    int version
) {
    public UserDto(User user) {
        this(
            user.getId(),
            user.getUsername(),
            user.getVersion()
        );
    }
}
