package dev.mayra.cursos.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private Integer idUser;
  private String userName;
  private String email;
  private String password;
  private String role;
  private LocalDate createdAt;
}
