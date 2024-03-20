package dev.mayra.cursos.domain.entities;

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
public class Role implements GrantedAuthority {
  private Integer idRole;
  private String name;
  private ArrayList<User> usuarios = new ArrayList<>();

  @Override
  public String getAuthority() {
    return name;
  }
}
