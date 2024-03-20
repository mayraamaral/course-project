package dev.mayra.courses.entities.user;

import dev.mayra.courses.entities.role.RoleDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
  private Integer idUser;
  private String username;
  private String name;
  private String email;
  private LocalDate createdAt;
  private RoleDTO role;
}
