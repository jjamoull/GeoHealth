package com.webgis.user;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import com.webgis.user.dto.UpdatePasswordDto;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private CookieService cookieService;

    @MockBean
    private JwtService jwtService;


    @Test
    void testGetUserProfileFound() throws Exception {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");

        Mockito.when(cookieService.getJwtFromCookie(any(HttpServletRequest.class))).thenReturn("fakeToken");
        Mockito.when(jwtService.extractUsername("fakeToken")).thenReturn(user.getUsername());
        Mockito.when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));


        //Act
        ResultActions response = mockMvc.perform(get("/users/profile")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.LastName").value(user.getLastName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.role").value(user.getRole()));

    }

    @Test
    void testGetUserProfileNotFound() throws Exception {
        //Arrange
        Mockito.when(cookieService.getJwtFromCookie(any(HttpServletRequest.class))).thenReturn("fakeToken");
        Mockito.when(jwtService.extractUsername("fakeToken")).thenReturn("");
        Mockito.when(userService.findByUsername("")).thenReturn(Optional.empty());

        //Act
        ResultActions response = mockMvc.perform(
                get("/users/profile")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    void changePasswordSuccess() throws Exception{
        //Arrange
        UpdatePasswordDto updatePasswordDto= new UpdatePasswordDto(
                "jajmoulle",
                "oldPassword",
                "newPassword");

        Mockito.when(cookieService.getJwtFromCookie(any(HttpServletRequest.class))).thenReturn("fakeToken");
        Mockito.when(jwtService.extractUsername("fakeToken")).thenReturn(updatePasswordDto.username());
        Mockito.when(userService.changePassword(updatePasswordDto.username(), updatePasswordDto.oldPassword(), updatePasswordDto.newPassword())).thenReturn(new User());

        //Act
        ResultActions response = mockMvc.perform(
                put("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto))
        );


        //Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed successfully"));
    }

}





