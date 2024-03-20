package dev.mayra.courses.entities.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
  @NotBlank(message = "The username can't be blank")
  @Schema(description = "Fill the username", required = true, example = "namelastname")
  private String username;

  @NotBlank(message = "The password can't be blank")
  @Schema(description = "Fill the password", required = true, example = "mypassword")
  private String password;
}
