package com.webgis.admin;

import com.webgis.MessageDto;
import com.webgis.admin.dto.ChangeRoleDto;
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

    /**
     * returns all the users info
     *
     * @return a list of all the users as a list of UserResponseDto
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        final List<User> users = userService.findAllUsers();
        final List<UserResponseDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(new UserResponseDto(user));
        }
        return ResponseEntity.status(200).body(result);
    }

    /**
     * Bans the user
     * @param username the username of the target to ban
     * @return confirmation message if succeeded, error message otherwise
     */
    @PutMapping("/users/{username}/ban")
    public ResponseEntity<Object> banUser(@PathVariable String username){
        try {
            userService.banUser(username);
            return ResponseEntity.status(200).body(new MessageDto("User has been banned"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    /**
     * Unbans the user
     * @param username the username of the target to unban
     * @return confirmation message if succeeded, error message otherwise
     */
    @PutMapping("/users/{username}/unban")
    public ResponseEntity<MessageDto> unbanUser(@PathVariable String username){
        try {
            userService.unbanUser(username);
            return ResponseEntity.status(200).body(new MessageDto("User has been unbanned"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

    /**
     * changes the user's role
     * @param username the username of the target to unban
     * @return confirmation message if succeeded, error message otherwise
     */
    @PutMapping("/users/{username}/change-role")
    public ResponseEntity<MessageDto> changeRole(
            @PathVariable String username,
            @RequestBody ChangeRoleDto changeRoleDto
    ){
        try{
            userService.changeRole(username, changeRoleDto.role());
            return ResponseEntity.status(200).body(new MessageDto("role udpated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }

}