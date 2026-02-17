package com.webgis.user;



import com.webgis.MessageDto;
import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.dto.DeleteAccountDto;
import com.webgis.user.dto.UserResponseDto;
import com.webgis.user.dto.UserUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtService jwtService, CookieService cookieService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.cookieService= cookieService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getUserByUsername(HttpServletRequest request){
        String token = cookieService.getJwtFromCookie(request);
        String username = jwtService.extractUsername(token);
        Optional<User> user= userService.findByUsername(username);

        if(user.isPresent()){
            UserResponseDto userResponseDto= new UserResponseDto(user.get());
            return ResponseEntity.status(200).body(userResponseDto);
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(
            HttpServletRequest request,
            @RequestBody UserUpdateDto updateDto
    ){
        String token = cookieService.getJwtFromCookie(request);
        String username = jwtService.extractUsername(token);
        Optional<User> userOptional = userService.findByUsername(username);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        try{
            User updatedUser = userService.updateUserInfo(
                    username,
                    updateDto.username(),
                    updateDto.firstName(),
                    updateDto.lastName(),
                    updateDto.email()
            );

            UserResponseDto userResponseDto = new UserResponseDto(updatedUser);
            return ResponseEntity.status(200).body(userResponseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
            HttpServletRequest request,
            @RequestBody DeleteAccountDto deleteAccountDto
    ){

        String token = cookieService.getJwtFromCookie(request);
        String username = jwtService.extractUsername(token);
        Optional<User> userOptional = userService.findByUsername(username);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        User user = userOptional.get();

        if(!username.equals(deleteAccountDto.username())) {
            throw new IllegalArgumentException("Wrong username");
        }
        if(!Objects.equals(passwordEncoder.encode(deleteAccountDto.password()), user.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }
        return ResponseEntity.status(200).body(new MessageDto("your account has been deleted successfully"));
    }
}