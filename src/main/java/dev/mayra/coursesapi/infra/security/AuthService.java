package dev.mayra.coursesapi.infra.security;

import com.auth0.jwt.JWT;
import dev.mayra.coursesapi.model.dtos.user.UserLoginDTO;
import dev.mayra.coursesapi.model.dtos.user.UserResponseDTO;
import dev.mayra.coursesapi.model.entities.User;
import dev.mayra.coursesapi.services.UserService;
import dev.mayra.coursesapi.utils.exceptions.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager,
        UserService userService) {

        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public UserResponseDTO getLoggedUser(String token) throws NotFoundException {
        String id = String.valueOf(JWT.decode(token).getClaim("id"));

        Long idLong = Long.parseLong(id);

        return userService.findById(idLong);
    }

    public String auth(UserLoginDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        User userAuth = (User) authentication.getPrincipal();

        return tokenService.generateToken(userAuth);
    }
}
