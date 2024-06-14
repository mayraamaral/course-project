package dev.mayra.coursesapi.model.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.mayra.coursesapi.model.entities.User;
import dev.mayra.coursesapi.model.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserResponseDTO {
    private Long idUser;
    private String username;
    private String name;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    private String roleName;

    public UserResponseDTO() {}

    public UserResponseDTO(Long idUser, String username, String name, String email, LocalDate createdAt, Role role) {
        this.idUser = idUser;
        this.username = username;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.roleName = role.name();
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getRoleName() {
        return roleName;
    }
}
