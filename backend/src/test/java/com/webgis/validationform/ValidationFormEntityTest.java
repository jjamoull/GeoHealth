package com.webgis.validationform;

import com.webgis.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationFormEntityTest {

    @Test
    void getterTest(){
        //Arrange
        User user = new User("pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin");

        ValidationForm validationForm=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                true
        );

        //Assert
        assertEquals("Wouri", validationForm.getDepartment());
        assertEquals(2, validationForm.getAgreementLevel());
        assertEquals("low", validationForm.getPerceivedRisk());
        assertEquals(4, validationForm.getCertaintyLevel());
        assertEquals("comment", validationForm.getComment());
        assertEquals(user, validationForm.getUser());
        assertTrue(validationForm.isPublic());

    }

    @Test
    void setterTest(){
        // Arrange
        User user1 = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        User user2 = new User(
                "jdp",
                "Jean",
                "Dupont",
                "jean.dupont@test.com",
                "password2",
                "User"
        );

        ValidationForm validationForm = new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user1,
                true
        );

        // Act
        validationForm.setDepartment("Mfoundi");
        validationForm.setAgreementLevel(4);
        validationForm.setPerceivedRisk("high");
        validationForm.setCertaintyLevel(1);
        validationForm.setComment("new comment");
        validationForm.setUser(user2);
        validationForm.setIsPublic(false);

        // Assert
        assertEquals("Mfoundi", validationForm.getDepartment());
        assertEquals(4, validationForm.getAgreementLevel());
        assertEquals("high", validationForm.getPerceivedRisk());
        assertEquals(1, validationForm.getCertaintyLevel());
        assertEquals("new comment", validationForm.getComment());
        assertEquals(user2, validationForm.getUser());
        assertFalse(validationForm.isPublic());
    }
}
