package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.UserRepository;
import dev.mayra.courses.utils.LeastPrivilegedRole;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

  public UserResponseDTO findById(Integer id) throws Exception {
    Optional<User> user = userRepository.findById(id);

    if(user.isEmpty()) {
      throw new Exception("User not found");
    }

    return mapper.convertToDTO(user.get(), UserResponseDTO.class);
  }

  public UserResponseDTO create(UserCreateDTO userToBeCreated) throws Exception {

    if(userRepository.findByUsername(userToBeCreated.getUsername()).isPresent()) {
      throw new Exception("Username already taken");
    }

    if(userRepository.findByEmail(userToBeCreated.getEmail()).isPresent()) {
      throw new Exception("Email already registered");
    }

    User user = mapper.convertToEntity(userToBeCreated, User.class);
    String encryptedPassword = passwordEncoder.encode(userToBeCreated.getPassword());
    user.setPassword(encryptedPassword);
    user.setCreatedAt(LocalDate.now());
    user.setRole(leastPrivilegedRole.get());

    User userCreated = userRepository.save(user);

    return mapper.convertToDTO(userCreated, UserResponseDTO.class);
  }
}
