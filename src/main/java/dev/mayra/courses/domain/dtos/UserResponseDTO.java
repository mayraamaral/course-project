package dev.mayra.courses.domain.dtos;

import dev.mayra.courses.domain.entities.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
  private Integer idUser;
  private String userName;
  private String name;
  private String email;
  private String password;
  private LocalDate createdAt;
  private RoleDTO role;
}
