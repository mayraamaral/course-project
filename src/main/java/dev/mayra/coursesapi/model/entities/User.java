package dev.mayra.coursesapi.model.entities;

import dev.mayra.coursesapi.model.dtos.user.UserCreateDTO;
import dev.mayra.coursesapi.model.dtos.user.UserResponseDTO;
import dev.mayra.coursesapi.model.enums.Role;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column
    private String username;

    @Column
    private  String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

    public User() {}

    public User(UserCreateDTO user, PasswordEncoder passwordEncoder) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = passwordEncoder.encode(user.getPassword());
        this.role = Role.valueOf(user.getRoleName());
    }

    public User(UserResponseDTO user) {
        this.idUser = user.getIdUser();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = Role.valueOf(user.getRoleName());
    }

    public void update(UserCreateDTO user, PasswordEncoder passwordEncoder) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = passwordEncoder.encode(user.getPassword());
        this.role = Role.valueOf(user.getRoleName());
    }

    public UserResponseDTO toUserResponseDTO() {
        return new UserResponseDTO(idUser, username, name, email, createdAt, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if(isAdmin()) {
//            return Stream.of(Role.values())
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toList());
//        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRoleName()));
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

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public boolean isInstructor() {
        return role.equals(Role.INSTRUCTOR);
    }

    public boolean isStudent() {
        return role.equals(Role.STUDENT);
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getRoleName() {
        return role.name();
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
