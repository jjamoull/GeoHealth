package com.webgis.user;

import com.webgis.security.CookieService;
import com.webgis.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CookieService cookieService;

    @MockBean
    private JwtService jwtService;

    @Test
    void statusHasCookie() throws Exception{
        //Arrange
        Mockito.when(cookieService.getJwtFromCookie(any(HttpServletRequest.class))).thenReturn("fakeToken");
        Mockito.when(jwtService.isTokenValid("fakeToken")).thenReturn(true);

        //Act
        ResultActions response = mockMvc.perform(get("/auth/status")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You are logged in"));
    }

    @Test
    void statusNoCookie() throws Exception{
        //Arrange
        Mockito.when(cookieService.getJwtFromCookie(any(HttpServletRequest.class))).thenReturn(null);
        Mockito.when(jwtService.isTokenValid(null)).thenReturn(false);

        //Act
        ResultActions response = mockMvc.perform(get("/auth/status")
                .contentType(MediaType.APPLICATION_JSON));

        //Assert
        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("You are not logged in"));
    }

}
