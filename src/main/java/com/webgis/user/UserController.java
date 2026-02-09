package com.webgis.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/save")
    public User addUser(@RequestBody User user){
        return userService.saveUser(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
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
                user.getPassword()
        );
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user.getId().intValue());
    }
}