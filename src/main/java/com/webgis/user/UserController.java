package com.webgis.user;

import com.webgis.MessageDto;
import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.dto.DeleteAccountDto;
import com.webgis.user.dto.UserResponseDto;
import com.webgis.user.dto.UserUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;

    public UserController(UserService userService, JwtService jwtService, CookieService cookieService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.cookieService= cookieService;
    }

    /**
     * Retrieves the authenticated user's profile information
     *
     * @param request the Http request containing the JWT token
     * @return user profile information if found, otherwise 404 error
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getUserByUsername(HttpServletRequest request){
        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> user= userService.findByUsername(username);

        if(user.isPresent()){
            final UserResponseDto userResponseDto= new UserResponseDto(user.get());
            return ResponseEntity.status(200).body(userResponseDto);
        }
        return ResponseEntity.status(404).build();
    }

    /**
     * Update the authenticated user's profile information
     *
     * @param request the Http request containing the JWT token
     * @param updateDto the new user information
     * @param response the HTTP response
     * @return updated user information if successful, error message otherwise
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUserInfo(
            HttpServletRequest request,
            @RequestBody UserUpdateDto updateDto,
            HttpServletResponse response
    ){
        final String token = cookieService.getJwtFromCookie(request);
        final String username = jwtService.extractUsername(token);
        final Optional<User> userOptional = userService.findByUsername(username);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        try{
            final User updatedUser = userService.updateUserInfo(
                    username,
                    updateDto.username(),
                    updateDto.firstName(),
                    updateDto.lastName(),
                    updateDto.email()
            );

            if (!username.equals(updateDto.username())) {
                final String newToken = jwtService.generateToken(updatedUser);
                final String newCookie = cookieService.generateCookie(newToken);
                response.addHeader(HttpHeaders.SET_COOKIE, newCookie);
            }

            final UserResponseDto userResponseDto = new UserResponseDto(updatedUser);
            return ResponseEntity.status(200).body(userResponseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
            HttpServletRequest request,
            @RequestBody DeleteAccountDto deleteAccountDto,
            HttpServletResponse response
    ){
        String token = cookieService.getJwtFromCookie(request);
        String username = jwtService.extractUsername(token);
        try{
            if (!username.equals(deleteAccountDto.username())){
                throw new IllegalArgumentException("Wrong username");
            }
            userService.deleteUser(deleteAccountDto.username(), deleteAccountDto.password());
            final String cookie = cookieService.deleteCookie();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie);

            return ResponseEntity.status(200).body(new MessageDto("your account has been deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }
}