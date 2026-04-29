package com.webgis.admin.user;

import com.webgis.MessageDto;
import com.webgis.admin.dto.user.AdminUserDto;
import com.webgis.admin.dto.user.BanDto;
import com.webgis.admin.dto.user.ChangeRoleDto;
import com.webgis.user.User;
import com.webgis.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.com",
                "password",
                "Admin");
        user.setId(1L);
    }


    // getAllUser Test

    @Test
    void getAllUsersShouldReturnOkWithListOfUsersTest() {
        // Arrange
        when(userService.findAllUsers()).thenReturn(List.of(user));

        // Act
        ResponseEntity<List<AdminUserDto>> response = adminController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllUsersShouldReturnOkWithEmptyListWhenNoUsersTest() {
        // Arrange
        when(userService.findAllUsers()).thenReturn(List.of());

        // Act
        ResponseEntity<List<AdminUserDto>> response = adminController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

   // banUser Test
    @Test
    void banUserShouldReturnOkWhenUserExistsTest() {
        // Arrange
        BanDto banDto = new BanDto("pseudo", "disturb other users");
        doNothing().when(userService).banUser("pseudo");

        // Act
        ResponseEntity<Object> response = adminController.banUser(banDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService).banUser("pseudo");
    }

    @Test
    void banUserShouldReturnBadRequestWhenUserDoesNotExistTest() {
        // Arrange
        BanDto banDto = new BanDto("unknown", "no reason");
        doThrow(new IllegalArgumentException("User not found"))
                .when(userService).banUser("unknown");

        // Act
        ResponseEntity<Object> response = adminController.banUser(banDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    //UnbanUser Test

    @Test
    void unbanUserShouldReturnOkWhenUserExistsTest() {
        // Arrange
        doNothing().when(userService).unbanUser("pseudo");

        // Act
        ResponseEntity<MessageDto> response = adminController.unbanUser("pseudo");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService).unbanUser("pseudo");
    }

    @Test
    void unbanUserShouldReturnBadRequestWhenUserDoesNotExistTest() {
        // Arrange
        doThrow(new IllegalArgumentException("User not found"))
                .when(userService).unbanUser("unknown");

        // Act
        ResponseEntity<MessageDto> response = adminController.unbanUser("unknown");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

   //ChangeRole Test
    @Test
    void changeRoleShouldReturnOkWhenValidTest() {
        // Arrange
        ChangeRoleDto changeRoleDto = new ChangeRoleDto("pseudo", "ADMIN");
        doNothing().when(userService).changeRole("pseudo", "ADMIN");

        // Act
        ResponseEntity<MessageDto> response = adminController.changeRole(changeRoleDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService).changeRole("pseudo", "ADMIN");
    }
}