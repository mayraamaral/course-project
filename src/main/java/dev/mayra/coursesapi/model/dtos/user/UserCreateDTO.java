package dev.mayra.coursesapi.model.dtos.user;

import dev.mayra.coursesapi.model.entities.User;
import dev.mayra.coursesapi.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserCreateDTO {
    @NotBlank(message = "The username can't be blank")
    @Schema(description = "Fill the username", required = true, example = "namelastname")
    @Pattern(regexp = "^[a-z]+$", message = "The username must contain only lowercase characters, without numerals and spaces")
    @Size(max = 20, message = "The username can have a maximum of 20 characters")
    private String username;

    @NotBlank(message = "The name can't be blank")
    @Schema(description = "Fill the name", required = true, example = "Name Lastname")
    private String name;

    @NotBlank(message = "The email can't be blank")
    @Email(message = "Must be a valid email")
    @Schema(description = "Fill the email", required = true, example = "email@email.com")
    private String email;

    @NotBlank(message = "The role can't be blank")
    @Schema(description = "Fill the role", required = true, example = "STUDENT")
    private String roleName;

    @NotBlank(message = "The password can't be blank")
    @Schema(description = "Fill the password", required = true, example = "mypassword")
    private String password;

    public UserCreateDTO() {}

    public UserCreateDTO(String username, String name, String email, String roleName, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.roleName = roleName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getPassword() {
        return password;
    }
}
