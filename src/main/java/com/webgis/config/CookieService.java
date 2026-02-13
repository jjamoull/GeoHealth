package com.webgis.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    private static final int COOKIEAGE = 3600;//1 heure
    private static final String COOKIENAME = "jwt";

    public String generateCookie(String token) {
        ResponseCookie cookie = ResponseCookie.from(COOKIENAME, token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(COOKIEAGE)
                .sameSite("Strict")
                .build();

        return cookie.toString();
    }

    public String deleteCookie() {
        ResponseCookie cookie = ResponseCookie.from(COOKIENAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return cookie.toString();
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}