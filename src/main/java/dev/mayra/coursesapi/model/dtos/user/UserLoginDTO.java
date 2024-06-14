package dev.mayra.coursesapi.model.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    @NotBlank(message = "The username can't be blank")
    @Schema(description = "Fill the username", required = true, example = "namelastname")
    private String username;

    @NotBlank(message = "The password can't be blank")
    @Schema(description = "Fill the password", required = true, example = "mypassword")
    private String password;

    public UserLoginDTO() {}

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank(message = "The username can't be blank") String getUsername() {
        return username;
    }

    public @NotBlank(message = "The password can't be blank") String getPassword() {
        return password;
    }
}
