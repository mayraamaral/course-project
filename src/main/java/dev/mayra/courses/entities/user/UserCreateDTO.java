package dev.mayra.courses.entities.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
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
  @Schema(description = "Fill the email", required = true, example = "email@email.com")
  private String email;

  @NotBlank(message = "The password can't be blank")
  @Schema(description = "Fill the password", required = true, example = "mypassword")
  private String password;
}
