package dev.mayra.courses.entities.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class UserCreateDTO {
  @NotBlank(message = "The username can't be blank")
  @Schema(description = "Fill the username", required = true, example = "namelastname")
  private String userName;

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
