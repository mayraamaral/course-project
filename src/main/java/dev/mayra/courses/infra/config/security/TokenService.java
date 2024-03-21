package dev.mayra.courses.infra.config.security;

import dev.mayra.courses.entities.user.User;

import dev.mayra.courses.entities.user.UserLoginDTO;
import dev.mayra.courses.utils.Mapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final Mapper mapper;

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.duration.days}")
  private String expDays;

  public String generateToken(User user) {
    Date now = new Date();

    List<String> roles = new ArrayList<>();
    roles.add(user.getRole().getAuthority());

    return Jwts.builder()
        .setIssuer("courses")
        .claim(Claims.ID, user.getIdUser().toString())
        .claim("roles", roles)
        .setIssuedAt(now)
        .setExpiration(genExpirationDate(now))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public UsernamePasswordAuthenticationToken validateToken(String token) {
    if (token != null) {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token.replace("Bearer ", ""))
          .getBody();
      UserLoginDTO user = mapper.convertToDTO(body, UserLoginDTO.class);
      if (user != null) {
        List<String> cargos = body.get("roles", List.class);
        List<SimpleGrantedAuthority> authorities = cargos.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(user,null, authorities);
      }
    }
    return null;
  }

  private Date genExpirationDate(Date date) {
    int expirationInDaysInt = Integer.parseInt(expDays);
    Date expirationDate = new Date(date.getTime() + TimeUnit.DAYS.toMillis(expirationInDaysInt));
    return expirationDate;
  }
}
