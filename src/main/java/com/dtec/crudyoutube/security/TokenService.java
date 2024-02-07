package com.dtec.crudyoutube.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.dtec.crudyoutube.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private String secret = "askjdhaksdhjskadç";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("auth").withSubject(user.getUsername()).withExpiresAt(getExpirationDate()).sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm).withIssuer("auth").build().verify(token).getSubject();

        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
