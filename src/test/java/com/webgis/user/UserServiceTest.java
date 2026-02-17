package com.webgis.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceTest {

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
    void updateUserInfoSuccessTest(){
        //Arrange
        User user = new User("pseudo", "Julien", "Jamal", "julien.jamal@outlook.com", "password", "Admin");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername("mypseudo")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("jean.jamal@outlook.com")).thenReturn(Optional.empty());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        //Act
        userService.updateUserInfo("mypseudo","mypseudo", "Jean", "Jamal", "jean.jamal@outlook.com");

        //Assert
        assertEquals("mypseudo", user.getUsername());
        assertEquals("Jean", user.getFirstName());
        assertEquals("Jamal", user.getLastName());
        assertEquals("jean.jamal@outlook.com", user.getEmail());
        assertEquals("Admin", user.getRole());

    }

    @Test
    void updateUserInfoUserNotFoundTest(){
        //Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserInfo("nonexistent", "newpseudo", "Jean", "Jamal", "jean.jamal@outlook.com");
        });
    }

}
