package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.role.Role;
import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserMinifiedDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.RoleRepository;
import dev.mayra.courses.infra.repositories.UserRepository;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.webjars.NotFoundException;

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

  public UserResponseDTO findById(Integer id) {
    Optional<User> user = userRepository.findById(id);

    if(user.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    return mapper.convertToDTO(user.get(), UserResponseDTO.class);
  }

  public UserMinifiedDTO findByUsername(String username) {
    Optional<UserMinifiedDTO> user = userRepository.findByUsernameMinified(username);

    if(user.isEmpty()) {
      throw new NotFoundException("Username not found");
    }

    return user.get();
  }

  public UserResponseDTO create(UserCreateDTO userToBeCreated) throws Exception {

    checkIfUsernameOrEmailAreTaken(userToBeCreated);

    User user = new User();
    Role role = getRoleByName(userToBeCreated.getRoleName());

    user.create(userToBeCreated, role, passwordEncoder);

    User userCreated = userRepository.save(user);

    return mapper.convertToDTO(userCreated, UserResponseDTO.class);
  }

  public UserResponseDTO update(Integer id, UserCreateDTO userUpdated) throws Exception {
    User user = findUserById(id);
    checkIfUsernameOrEmailAreTaken(userUpdated);

    user.update(
        userUpdated,
        getRoleByName(userUpdated.getRoleName()),
        passwordEncoder
    );

    User userFromDb = userRepository.save(user);

    return mapper.convertToDTO(userFromDb, UserResponseDTO.class);
  }

  public void delete(Integer id) {
    User user = findUserById(id);

    userRepository.delete(user);
  }

  public void checkIfUsernameOrEmailAreTaken(UserCreateDTO user) throws Exception {
    if(usernameExists(user.getUsername())) {
      throw new Exception("Username already taken");
    }

    if(emailExists(user.getEmail())) {
      throw new Exception("Email already registered");
    }
  }

  public User findUserByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);

    if(user.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    return user.get();
  }

  public User findUserById(Integer id) {
    Optional<User> user = userRepository.findById(id);

    if(user.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    return user.get();
  }

  public boolean isUserAnInstructor(Integer idUser) throws Exception {
    Role userRole = getUserRole(idUser);

    return userRole.getName().equals("ROLE_INSTRUCTOR");
  }

  public Role getUserRole(Integer idUser) {
    User user = findUserById(idUser);

    return user.getRole();
  }

  public boolean usernameExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean emailExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  public Role getRoleByName(String roleName) {
    Optional<Role> role = roleRepository.findByName(roleName);

    if(role.isEmpty()) throw new NotFoundException("Role not found");

    return role.get();
  }
}
