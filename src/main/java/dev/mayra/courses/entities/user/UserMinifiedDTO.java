package dev.mayra.courses.entities.user;

import dev.mayra.courses.entities.role.RoleDTO;
import lombok.Data;

@Data
public class UserMinifiedDTO {
  public String name;
  public String email;
  public RoleDTO role;
}
