package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.role.Role;
import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.RoleRepository;
import dev.mayra.courses.infra.repositories.UserRepository;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
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

    checkIfUsernameOrEmailAreTaken(userToBeCreated);

    User user = mapper.convertToEntity(userToBeCreated, User.class);
    user.setRole(getRoleByName(userToBeCreated.getRoleName()));
    user = getUserWithEncryptedPassword(user);
    user.setCreatedAt(LocalDate.now());

    User userCreated = userRepository.save(user);

    return mapper.convertToDTO(userCreated, UserResponseDTO.class);
  }

  public UserResponseDTO update(Integer id, UserCreateDTO userUpdated) throws Exception {
    User user = findUserById(id);
    checkIfUsernameOrEmailAreTaken(userUpdated);
    BeanUtils.copyProperties(userUpdated, user);
    User userFromDb = userRepository.save(user);

    return mapper.convertToDTO(userFromDb, UserResponseDTO.class);
  }

  public void delete(Integer id) throws Exception {
    User user = findUserById(id);

    userRepository.delete(user);
  }

  public User getUserWithEncryptedPassword(User user) {
    String encryptedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encryptedPassword);

    return user;
  }

  public void checkIfUsernameOrEmailAreTaken(UserCreateDTO user) throws Exception {
    if(usernameExists(user.getUsername())) {
      throw new Exception("Username already taken");
    }

    if(emailExists(user.getEmail())) {
      throw new Exception("Email already registered");
    }
  }

  public User findUserById(Integer id) throws Exception {
    Optional<User> user = userRepository.findById(id);

    if(user.isEmpty()) {
      throw new Exception("User not found");
    }

    return user.get();
  }

  public boolean usernameExists(String username) throws Exception {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean emailExists(String email) throws Exception {
    return userRepository.findByEmail(email).isPresent();
  }

  public Role getRoleByName(String roleName) throws Exception {
    Optional<Role> role = roleRepository.findByName(roleName);

    if(role.isEmpty()) throw new Exception("Role not found");

    return role.get();
  }
}
