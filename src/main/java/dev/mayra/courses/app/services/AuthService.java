package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.entities.user.UserLoginDTO;
import dev.mayra.courses.infra.config.security.SecurityFilter;
import dev.mayra.courses.infra.config.security.TokenService;
import dev.mayra.courses.infra.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;
  private final SecurityFilter securityFilter;

  private final UserService userService;

  @Value("${api.security.token.secret}")
  private String secret;

  public User getLoggedUser(String token) {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token.replace("Bearer ", ""))
          .getBody();

      Integer idUser = Integer.parseInt(body.getId());

      User user = userService.findUserById(idUser);

      return user;
  }

  public String auth(UserLoginDTO login) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    User userAuth = (User) authentication.getPrincipal();

    return tokenService.generateToken(userAuth);
  }

}
