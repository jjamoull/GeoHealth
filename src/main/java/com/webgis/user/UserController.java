package com.webgis.user;



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

    @GetMapping("/isAdmin/{id}")
    public Boolean getUser(@PathVariable long id){
        Optional<User> userTemp = userService.findById(id);
        if (userTemp.isPresent()){
            User user = userTemp.get();
            if (user.getRole().equals("Admin")){
                return true;
            }
        }
        return false;
    }

    @PostMapping("/save")
    public User addUser(@RequestBody User user){
        return userService.saveUser(
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