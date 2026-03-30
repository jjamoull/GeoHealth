package com.webgis.admin.user;

import com.webgis.MessageDto;
import com.webgis.user.User;
import com.webgis.user.UserService;


import com.webgis.admin.dto.user.AdminUserDto;
import com.webgis.admin.dto.user.BanDto;
import com.webgis.admin.dto.user.ChangeRoleDto;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


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
     * @return a list of all the users as a list of AdminUserDto
     */
    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        final List<User> users = userService.findAllUsers();
        final List<AdminUserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(new AdminUserDto(user));
        }
        return ResponseEntity.status(200).body(result);
    }

    /**
     * Bans the u
     * @param banDto the information about the target to ban
     * @return confirmation message if succeeded, error message otherwise
     */
    @PutMapping("/users/ban")
    public ResponseEntity<Object> banUser(@RequestBody BanDto banDto){
        try {
            userService.banUser(banDto.username());
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
    @PutMapping("/users/unban/{username}")
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
     * @param changeRoleDto new role information
     * @return confirmation message if succeeded, error message otherwise
     */
    @PutMapping("/users/changeRole")
    public ResponseEntity<MessageDto> changeRole(
            @RequestBody ChangeRoleDto changeRoleDto
    ){
        try{
            userService.changeRole(changeRoleDto.username(), changeRoleDto.role());
            return ResponseEntity.status(200).body(new MessageDto("role udpated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new MessageDto(e.getMessage()));
        }
    }


}