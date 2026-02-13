package com.webgis.user;

import com.webgis.config.CookieService;
import com.webgis.config.JwtService;
import com.webgis.user.dto.LoginDto;
import com.webgis.MessageDto;
import com.webgis.user.dto.RegisterDto;
import com.webgis.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;

    public AuthController(UserService userService, JwtService jwtService, CookieService cookieService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }

    /**
     * Registers a new user account.
     *
     * @param registerDto the registration data
     * @param request the HTTP request
     * @param response the HTTP response
     * @return user information if successful, error message otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegisterDto registerDto,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        String existingCookie = cookieService.getJwtFromCookie(request);
        if (existingCookie != null && jwtService.isTokenValid(existingCookie)) {
            return ResponseEntity.status(409).body(new MessageDto("You are logged in"));
        }

        try {
            User user = userService.register(
                    registerDto.username(),
                    registerDto.firstName(),
                    registerDto.lastName(),
                    registerDto.email(),
                    registerDto.password(),
                    "USER"
            );
            return createResponse(user, response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new MessageDto(e.getMessage()));
        }
    }

    /**
     * Authenticates an existing user.
     *
     * @param loginDto the login credentials
     * @param request the HTTP request
     * @param response the HTTP response
     * @return user information if successful, error message otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody @Valid LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        String existingCookie = cookieService.getJwtFromCookie(request);
        System.out.println("Existing cookie: " + existingCookie);
        if (existingCookie != null && jwtService.isTokenValid(existingCookie)) {
            return ResponseEntity.status(409).body(new MessageDto("You are already logged in"));
        }

        try {
            User user = userService.login(loginDto.username(), loginDto.password());
            return createResponse(user, response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new MessageDto(e.getMessage()));
        }
    }

    /**
     * Checks whether a user is authenticated.
     *
     * @param request the HTTP request
     * @return message indicating authentication status
     */
    @GetMapping("/status")
    public ResponseEntity<?> checkAuthStatus(HttpServletRequest request){
        String existingCookie = cookieService.getJwtFromCookie(request);
        if (existingCookie != null && jwtService.isTokenValid(existingCookie)) {
            return ResponseEntity.status(200).body(new MessageDto("You are logged in"));
        }
        return ResponseEntity.status(401).body(new MessageDto("You are not logged in"));
    }

    /**
     * Logs out the user.
     *
     * @param response the HTTP response
     * @return confirmation message
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response){
        String cookie = cookieService.deleteCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.status(200).body(new MessageDto("Logged out successfully"));
    }

    /**
     * Creates a response with user information and authentication cookie.
     *
     * @param user the authenticated user
     * @param response the HTTP response
     * @return user information response
     */
    private ResponseEntity<UserResponseDto> createResponse(User user, HttpServletResponse response) {
        String token = jwtService.generateToken(user);
        String cookie = cookieService.generateCookie(token);
        System.out.println("Setting cookie: " + cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);

        UserResponseDto userResponseDto = new UserResponseDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
        return ResponseEntity.status(200).body(userResponseDto);
    }

}