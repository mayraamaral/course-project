package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.role.Role;
import dev.mayra.courses.entities.role.RoleDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserMinifiedDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.infra.repositories.RoleRepository;
import dev.mayra.courses.infra.repositories.UserRepository;
import dev.mayra.courses.utils.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Tests")
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private Mapper mapper;

  @Spy
  @InjectMocks
  private UserService userService;

  private static LocalDate currentDate = LocalDate.now();

  @Test
  public void testShouldListAllWithSuccess() {
    List<User> usersList = List.of(getUserMock(), getUserMock(), getUserMock());
    List<UserResponseDTO> usersDtoList = List.of(getUserResponseDtoMock(), getUserResponseDtoMock(), getUserResponseDtoMock());

    when(userRepository.findAll()).thenReturn(usersList);

    UserResponseDTO userResponse = getUserResponseDtoMock();

    when(mapper.convertToDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponse);

    List<UserResponseDTO> responseList = userService.listAllUsers();

    assertEquals(usersDtoList, responseList);
    assertEquals(usersDtoList.size(), responseList.size());
  }

  @Test
  public void testShouldListByIdWithSuccess() {
    User userMock = getUserMock();
    Optional<User> userOpt = Optional.of(userMock);
    when(userRepository.findById(any())).thenReturn(userOpt);

    UserResponseDTO userResponseMock = getUserResponseDtoMock();

    when(mapper.convertToDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseMock);

    UserResponseDTO responseUser = userService.findById(1);

    assertEquals(userResponseMock, responseUser);
  }

  @Test
  public void testShouldThrowNotFoundExceptionWhenInvalidIdIsPassed() {
    Optional<User> optMock = Optional.empty();

    when(userRepository.findById(any())).thenReturn(optMock);

    assertThrows(NotFoundException.class, () -> userService.findById(1));
  }

  @Test
  public void testShoudListByUsernameWithSuccess() {
    Optional<UserMinifiedDTO> userMock = Optional.of(getUserMinifiedDtoMock());

    when(userRepository.findByUsernameMinified(any())).thenReturn(userMock);

    UserMinifiedDTO userResponse = userService.findByUsername(any());

    assertEquals(userMock.get(), userResponse);
  }

  @Test
  public void testShoudThrowNotFoundWhenInvalidUsernameIsPassed() {
    Optional<UserMinifiedDTO> userOpt = Optional.empty();

    when(userRepository.findByUsernameMinified(any())).thenReturn(userOpt);

    assertThrows(NotFoundException.class, () -> userService.findByUsername(any()));
  }

  @Test
  public void testShouldCreateWithSuccess() throws Exception {
    UserCreateDTO userCreate = getUserCreateDtoMock();
    User userMock = getUserMock();
    Role roleMock = getAdminRoleMock();

    when(mapper.convertToEntity(any(UserCreateDTO.class), eq(User.class))).thenReturn(userMock);

    User userEncryptedPass = getUserWithEncryptedPassword();

    doReturn(userEncryptedPass).when(userService).getUserWithEncryptedPassword(any());
    doReturn(roleMock).when(userService).getRoleByName(any());

    when(userRepository.save(any())).thenReturn(userEncryptedPass);

    UserResponseDTO userResponseMock = getUserResponseDtoMock();

    when(mapper.convertToDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseMock);

    UserResponseDTO responseUser = userService.create(userCreate);

    assertEquals(userResponseMock, responseUser);
  }

  @Test
  public void testShoudUpdateWithSuccess() throws Exception {
    User userMock = getUserMock();
    UserCreateDTO userCreateMock = getUserCreateDtoMock();
    userCreateMock.setName("User updated");
    User userUpdated = getUserMock();
    userUpdated.setName("User updated");
    UserResponseDTO userResponseMock = getUserResponseDtoMock();
    userResponseMock.setName("User updated");

    doReturn(userMock).when(userService).findUserById(any());

    when(userRepository.save(userMock)).thenReturn(userUpdated);

    when(mapper.convertToDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseMock);

    UserResponseDTO responseUser = userService.update(1, userCreateMock);

    assertEquals(userUpdated.getName(), responseUser.getName());
  }

  @Test
  public void testShouldDeleteWithSuccess() {
    User userMock = getUserMock();

    doReturn(userMock).when(userService).findUserById(any());

    userService.delete(1);

    verify(userRepository, times(1)).delete(any());
  }

  @Test
  public void testShouldReturnUserWithEncryptedPassword() {
    User userEncryptMock = getUserWithEncryptedPassword();
    User userMock = getUserMock();

    String encryptedPass = "$2a$12$sL9FjSkJAo4l1k/0W6FzaO59/K3uUm0/5GdTVN0u4FeIinI4Zn8iG";

    when(passwordEncoder.encode(userMock.getPassword())).thenReturn(encryptedPass);

    User responseUser = userService.getUserWithEncryptedPassword(userMock);

    assertEquals(userEncryptMock.getPassword(), responseUser.getPassword());
  }

  @Test
  public void testShoutThrowExceptionIfUsernameIsTaken() throws Exception {
    Optional<User> userMock = Optional.of(getUserMock());
    UserCreateDTO userCreateMock = getUserCreateDtoMock();
    when(userRepository.findByUsername(any())).thenReturn(userMock);

    assertThrows(Exception.class, () -> userService.checkIfUsernameOrEmailAreTaken(userCreateMock));
  }

  @Test
  public void testShoutThrowExceptionIfEmailExists() throws Exception {
    Optional<User> userMock = Optional.of(getUserMock());
    UserCreateDTO userCreateMock = getUserCreateDtoMock();
    when(userRepository.findByEmail(any())).thenReturn(userMock);

    assertThrows(Exception.class, () -> userService.checkIfUsernameOrEmailAreTaken(userCreateMock));
  }

  @Test
  public void testShouldReturnUserFromUsername() {
    Optional<User> userOpt = Optional.of(getUserMock());

    when(userRepository.findByUsername(any())).thenReturn(userOpt);

    User responseUser = userService.findUserByUsername(any());

    assertEquals(userOpt.get(), responseUser);
  }

  @Test
  public void testShouldThrowExceptionFromUsernameIfUserDontExists() {
    Optional<User> userOpt = Optional.empty();

    when(userRepository.findByUsername(any())).thenReturn(userOpt);

    assertThrows(NotFoundException.class, () -> userService.findUserByUsername(any()));
  }

  @Test
  public void testShouldReturnUserFromId() {
    Optional<User> userOpt = Optional.of(getUserMock());

    when(userRepository.findById(any())).thenReturn(userOpt);

    User responseUser = userService.findUserById(any());

    assertEquals(userOpt.get(), responseUser);
  }

  @Test
  public void testShoudThrowExceptionFromIdIfUserDontExists() {
    Optional<User> userOpt = Optional.empty();

    when(userRepository.findById(any())).thenReturn(userOpt);

    assertThrows(NotFoundException.class, () -> userService.findUserById(any()));
  }

  public static User getUserMock() {
    User user = new User();
    user.setIdUser(1);
    user.setUsername("user");
    user.setName("User");
    user.setEmail("user@email.com");
    user.setPassword("password");
    user.setRole(getAdminRoleMock());
    user.setCreatedAt(currentDate);

    return user;
  }

  public static User getUserWithEncryptedPassword() {
    User user = getUserMock();
    user.setPassword("$2a$12$sL9FjSkJAo4l1k/0W6FzaO59/K3uUm0/5GdTVN0u4FeIinI4Zn8iG");
    return user;
  }

  public static UserCreateDTO getUserCreateDtoMock() {
    UserCreateDTO userCreate = new UserCreateDTO();
    userCreate.setUsername("user");
    userCreate.setName("User");
    userCreate.setEmail("user@email.com");
    userCreate.setRoleName("ROLE_ADMIN");
    userCreate.setPassword("password");

    return userCreate;
  }

  public static UserMinifiedDTO getUserMinifiedDtoMock() {
    UserMinifiedDTO userMinified = new UserMinifiedDTO();
    userMinified.setName("user");
    userMinified.setEmail("user@email.com");
    userMinified.setRole("ROLE_ADMIN");

    return userMinified;
  }

  public static UserResponseDTO getUserResponseDtoMock() {
    UserResponseDTO userResponse = new UserResponseDTO();
    userResponse.setIdUser(1);
    userResponse.setUsername("user");
    userResponse.setName("User");
    userResponse.setEmail("user@email.com");
    userResponse.setCreatedAt(currentDate);
    userResponse.setRole(getAdminRoleDtoMock());

    return userResponse;
  }

  public static RoleDTO getAdminRoleDtoMock() {
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setIdRole(1);
    roleDTO.setName("ROLE_ADMIN");

    return roleDTO;
  }

  public static Role getAdminRoleMock() {
    Role role = new Role();
    role.setIdRole(1);
    role.setName("ROLE_ADMIN");

    return role;
  }
}