package dev.mayra.courses.utils;

import dev.mayra.courses.domain.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LeastPrivilegedRole {
  private static int ROLE_ID = 3;
  private static String ROLE_NAME = "ROLE_STUDENT";
  public Role get() {
    Role role = new Role();
    role.setIdRole(ROLE_ID);
    role.setName(ROLE_NAME);

    return role;
  }
}
