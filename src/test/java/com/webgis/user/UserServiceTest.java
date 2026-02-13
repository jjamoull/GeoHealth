package com.webgis.user;

import com.webgis.user.User;
import com.webgis.user.UserRepository;
import com.webgis.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void UpdateUserServiceTest(){
        //Arrange
        User user = new User("pseudo", "Julien", "Jamal", "julien.jamal@outlook.com", "password", "Admin");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername("mypseudo")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("jean.jamal@outlook.com")).thenReturn(Optional.empty());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("password")).thenReturn("hashed_password");

        //Act
        userService.updateUser(1,"mypseudo", "Jean", "Jamal", "jean.jamal@outlook.com", "password", "Admin");

        //Assert
        assertEquals("mypseudo", user.getUsername());
        assertEquals("Jean", user.getFirstName());
        assertEquals("Jamal", user.getLastName());
        assertEquals("jean.jamal@outlook.com", user.getEmail());
        assertEquals("hashed_password", user.getPassword());
        assertEquals("Admin", user.getRole());

    }



}
