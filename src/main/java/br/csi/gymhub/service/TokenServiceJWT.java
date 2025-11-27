package br.csi.gymhub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceJWT {
    public String gerarToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("poo2");

            return JWT.create()
                    .withIssuer("API Avaliar Projetos")
                    .withSubject(user.getUsername())
                    .withClaim("ROLE", user.getAuthorities().stream().toList().get(0).toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        Algorithm algorithm = Algorithm.HMAC256("poo2");
        return JWT.require(algorithm)
                .withIssuer("API Avaliar Projetos")
                .build()
                .verify(tokenJWT)
                .getSubject();
    }
}
