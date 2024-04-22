package dev.mayra.courses.entities.user;

import dev.mayra.courses.entities.role.Role;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idUser;

  @Column(name = "user_name")
  private String username;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "role_id")
  private Role role;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void create(UserCreateDTO user, Role role, PasswordEncoder passwordEncoder) {
    this.username = user.getUsername();
    this.name = user.getName();
    this.email = user.getEmail();
    this.password = passwordEncoder.encode(user.getPassword());
    this.role = role;
    this.createdAt = LocalDate.now();
  }

  public void update(UserCreateDTO userUpdated, Role roleUpdated, PasswordEncoder passwordEncoder) {
    this.username = userUpdated.getUsername();
    this.name = userUpdated.getName();
    this.email = userUpdated.getEmail();
    this.password = passwordEncoder.encode(userUpdated.getPassword());
    this.role = roleUpdated;
  }

}
