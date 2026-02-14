package com.webgis.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Value("${cookie.secure}")
    private boolean secure;

    private static final String COOKIENAME = "jwt";

    private final JwtService jwtService;

    public CookieService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Generates a cookie from a given JWT token
     * The cookie is configured with security settings for authentication.
     *
     * @param token token to put in the cookie
     * @return cookie as a string
     */
    public String generateCookie(String token) {
        final ResponseCookie cookie = ResponseCookie.from(COOKIENAME, token)
                .httpOnly(true)
                //todo change .secure to true in production
                .secure(secure)
                .path("/")
                .maxAge(jwtService.getExpirationSeconds())
                .sameSite("Strict")
                .build();

        return cookie.toString();
    }

    /**
     * Deletes the cookie by setting its max age to 0
     *
     * @return cookie as an empty String
     */
    public String deleteCookie() {
        final ResponseCookie cookie = ResponseCookie.from(COOKIENAME, "")
                .httpOnly(true)
                //todo change .secure to true in production
                .secure(secure)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return cookie.toString();
    }

    /**
     * Extracts the JWT token from the cookie in the HTTP request
     *
     * @return the JWT token value if found, null otherwise
     */
    public String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}