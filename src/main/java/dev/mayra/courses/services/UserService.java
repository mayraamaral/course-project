package dev.mayra.courses.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayra.courses.domain.dtos.UserCreateDTO;
import dev.mayra.courses.domain.dtos.UserResponseDTO;
import dev.mayra.courses.domain.entities.User;
import dev.mayra.courses.repositories.UserRepository;
import dev.mayra.courses.utils.LeastPrivilegedRole;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final LeastPrivilegedRole leastPrivilegedRole;
  private final PasswordEncoder passwordEncoder;
  private final Mapper mapper;

  public List<UserResponseDTO> listAllUsers() {
    List<User> allUsers = userRepository.findAll();
    return allUsers.stream()
        .map(entity -> mapper.convertToDTO(entity, UserResponseDTO.class))
        .collect(Collectors.toList());
  }

  public UserResponseDTO create(UserCreateDTO userToBeCreated) {
    User user = mapper.convertToEntity(userToBeCreated, User.class);
    user.setCreatedAt(LocalDate.now());
    user.setRole(leastPrivilegedRole.get());

    User userCreated = userRepository.save(user);

    return mapper.convertToDTO(userCreated, UserResponseDTO.class);
  }
}
