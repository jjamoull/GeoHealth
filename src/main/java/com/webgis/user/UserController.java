package com.webgis.user;



import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
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

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserByUsername(HttpServletRequest request){
        String token = cookieService.getJwtFromCookie(request);
        String username = jwtService.extractUsername(token);
        Optional<User> user= userService.findByUsername(username);

        if(user.isPresent()){
            UserResponseDto userResponseDto= new UserResponseDto(user.get());
            return ResponseEntity.ok().body(userResponseDto);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/isAdmin/{id}")
    public Boolean getUser(@PathVariable long id){
        final Optional<User> userTemp = userService.findById(id);
        if (userTemp.isPresent()){
            final User user = userTemp.get();
            if (user.getRole().equals("Admin")){
                return true;
            }
        }
        return false;
    }

    @PostMapping("/save")
    public User addUser(@RequestBody User user){
        return userService.register(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(
                user.getId().intValue(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user.getId().intValue());
    }
}