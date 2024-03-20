package dev.mayra.courses.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayra.courses.domain.dtos.UserResponseDTO;
import dev.mayra.courses.domain.entities.User;
import dev.mayra.courses.repositories.UserRepository;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final Mapper mapper;

  public List<UserResponseDTO> listAllUsers() {
    List<User> allUsers = userRepository.findAll();
    return allUsers.stream()
        .map(entity -> mapper.convertToDTO(entity, UserResponseDTO.class))
        .collect(Collectors.toList());
  }
}
