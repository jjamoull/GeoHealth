package com.webgis.admin;

import com.webgis.MessageDto;
import com.webgis.user.User;
import com.webgis.user.UserService;
import com.webgis.user.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        final List<User> users = userService.findAllUsers();
        final List<UserResponseDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserResponseDto(user));
        }
        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/users/{username}/ban")
    public ResponseEntity<Object> banUser(@PathVariable String username){
        try {
            userService.banUser(username);
            return ResponseEntity.status(200).body(new MessageDto("User has been banned"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    @PutMapping("/users/{username}/unban")
    public ResponseEntity<Object> unbanUser(@PathVariable String username){
        try {
            userService.unbanUser(username);
            return ResponseEntity.status(200).body(new MessageDto("User has been unbanned"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

}