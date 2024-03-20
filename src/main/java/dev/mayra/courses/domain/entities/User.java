package dev.mayra.courses.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

  @Id
  @Column(name = "user_id")
  private Integer idUser;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private Role role;

  @Column(name = "user_id")
  private LocalDate createdAt;
}
