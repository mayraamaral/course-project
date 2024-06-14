package dev.mayra.coursesapi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.mayra.coursesapi.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.duration.days}")
    private String expDays;

    @Value("${spring.application.name}")
    private String appName;

    public TokenService() {}

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                .withIssuer(appName)
                .withSubject(user.getUsername())
                .withExpiresAt(getExpirationDate())
                .withClaim("idUser", user.getIdUser())
                .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(appName)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Date getExpirationDate() {
        int expirationInDaysInt = Integer.parseInt(expDays);

        return new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(expirationInDaysInt));
    }
}
