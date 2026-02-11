package com.webgis.user;

import com.webgis.config.JwtService;
import com.webgis.user.dto.LoginDto;
import com.webgis.user.dto.RegisterDto;
import com.webgis.user.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid RegisterDto registerDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader
        ){
        if (authHeader != null){
            String token = authHeader.substring(7);
            if (jwtService.isTokenValid(token)){
                return ResponseEntity.status(400).body("You are already logged in");
                }
            }
        User user = userService.register(
                registerDto.username(),
                registerDto.firstName(),
                registerDto.lastName(),
                registerDto.email(),
                registerDto.password(),
                "USER"
        );

        String token = jwtService.generateToken(user);

        ResponseDto response = new ResponseDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                token
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody @Valid LoginDto loginDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader
        ){
        if (authHeader != null){
            String token = authHeader.substring(7);
            if (jwtService.isTokenValid(token)){
                return ResponseEntity.status(400).body("You are already logged in");
                }
            }

        User user = userService.login(loginDto.username(), loginDto.password());
        String token = jwtService.generateToken(user);
        ResponseDto response = new ResponseDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                token
        );
        return ResponseEntity.ok(response);
    }

}