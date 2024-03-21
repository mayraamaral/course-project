package dev.mayra.courses.infra.config.security;

import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
  private final UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userEntity = userRepository.findByUsername(username);

    return userEntity
        .orElseThrow(() -> new UsernameNotFoundException("Invalid user"));
  }
}
