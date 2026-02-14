package com.webgis.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    private static final int COOKIEAGE = 3600;//1 heure
    private static final String COOKIENAME = "jwt";

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
                .secure(false)
                .path("/")
                .maxAge(COOKIEAGE)
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
                .secure(false)
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