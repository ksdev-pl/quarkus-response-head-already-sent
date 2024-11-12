package pl.ksdev.qadmin.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;

public record UserForm (
    @NotBlank(message = "Username is required")
    @Email(message = "Invalid email format")
    @Size(max = 1000, message = "Username too long (max 1000 characters)")
    @FormParam("username")
    String username,

    @NotBlank(message = "Password is required")
    @Size(max = 500, message = "Password too long (max 500 characters)")
    @FormParam("password")
    String password
) {}
