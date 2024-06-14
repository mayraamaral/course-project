package dev.mayra.coursesapi.services;

import dev.mayra.coursesapi.infra.repositories.UserRepository;
import dev.mayra.coursesapi.model.dtos.user.UserCreateDTO;
import dev.mayra.coursesapi.model.dtos.user.UserResponseDTO;
import dev.mayra.coursesapi.model.entities.User;
import dev.mayra.coursesapi.utils.exceptions.AlreadyInUseException;
import dev.mayra.coursesapi.utils.exceptions.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> listAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(User::toUserResponseDTO)
            .collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"))
            .toUserResponseDTO();
    }

    public UserResponseDTO findByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"))
            .toUserResponseDTO();
    }

    public UserResponseDTO create(UserCreateDTO user) throws AlreadyInUseException {
        checkIfUsernameOrEmailAreTaken(user);

        return userRepository.save(new User(user, passwordEncoder))
            .toUserResponseDTO();
    }

    public UserResponseDTO update(Long id, UserCreateDTO userUpdated)
        throws NotFoundException, AlreadyInUseException {

        UserResponseDTO userFound = findById(id);
        User user = new User(userFound);

        if(didUsernameChanged(id, userUpdated.getUsername())) {
            checkIfUsernameIsTaken(userUpdated.getUsername());
        }

        if(didEmailChanged(id, userUpdated.getEmail())) {
            checkIfEmailIsTaken(userUpdated.getEmail());
        }

        user.update(userUpdated, passwordEncoder);

        return userRepository.save(user).toUserResponseDTO();
    }

    public void deleteById(Long id) throws NotFoundException {
        User user = new User(findById(id));

        userRepository.delete(user);
    }

    private void checkIfUsernameOrEmailAreTaken(UserCreateDTO user) throws AlreadyInUseException {
        checkIfUsernameIsTaken(user.getUsername());
        checkIfEmailIsTaken(user.getEmail());
    }

    private void checkIfUsernameIsTaken(String username) throws AlreadyInUseException {
        if(usernameExists(username)) {
            throw new AlreadyInUseException("Username already in use");
        }
    }

    private void checkIfEmailIsTaken(String email) throws AlreadyInUseException {
        if(emailExists(email)) {
            throw new AlreadyInUseException("Email already in use");
        }
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean didUsernameChanged(Long id, String username) {
        return userRepository.findById(id)
            .filter(user -> !user.getUsername().equals(username)).isPresent();
    }

    private boolean didEmailChanged(Long id, String email) {
        return userRepository.findById(id)
            .filter(user -> !user.getEmail().equals(email)).isPresent();
    }
}
