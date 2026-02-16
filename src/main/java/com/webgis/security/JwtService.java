package com.webgis.security;

import com.webgis.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @Value("${jwt.expiration.seconds}")
    private int expirationSeconds;

    @PostConstruct
    public void init() {
        try {
            final MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            final byte[] Bytes = sha256.digest(secret.getBytes(StandardCharsets.UTF_8));
            this.key = Keys.hmacShaKeyFor(Bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash", e);
        }
    }

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
                .expiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000L))
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

    /**
     * Gets the token expiration
     *
     * @return expiration time in seconds
     */
    public int getExpirationSeconds() {
        return expirationSeconds;
    }

}
