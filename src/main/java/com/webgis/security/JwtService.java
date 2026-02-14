package com.webgis.security;

import com.webgis.user.User;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    public static final int EXPIRATION = 3600 * 1000;

    /**
     * Generates a JWT token for a given user
     *
     * @param user the user used to generate the token
     * @return the generated JWT token as a string
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * Validates if a JWT token is valid
     *
     * @param token the JWT token to validate
     * @return true fi token is valide, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the username from a JWT token
     *
     * @param token the JWT token to extract the username from
     * @return the username if extraction is successful, null otherwise
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
