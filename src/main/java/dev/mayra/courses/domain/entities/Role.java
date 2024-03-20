package dev.mayra.courses.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
  @Id
  @Column(name = "role_id")
  private Integer idRole;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "role", orphanRemoval = true)
  private ArrayList<User> usuarios = new ArrayList<>();

  @Override
  public String getAuthority() {
    return name;
  }
}
