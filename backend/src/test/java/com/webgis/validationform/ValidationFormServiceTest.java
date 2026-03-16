package com.webgis.validationform;

import com.webgis.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationFormServiceTest {

    @Mock
    private ValidationFormRepository validationFormRepository;

    @InjectMocks
    private ValidationFormService validationFormService;

    @Test
    void updateFormFound(){
        // Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        ValidationForm validationForm= new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                true
        );
        validationForm.setId(1);

        when(validationFormService.findFormById(validationForm.getId())).thenReturn(Optional.of(validationForm));
        when(validationFormRepository.save(validationForm)).thenReturn(validationForm);

        //Act
        ValidationForm updatedValidationForm= validationFormService.updateForm(
                validationForm.getId(),
                "Mfoundi",
                4,
                "medium",
                3,
                "newComment",
                user,
                false
        );

        //Assert
        assertEquals("Mfoundi", validationForm.getDepartment());
        assertEquals(4, validationForm.getAgreementLevel());
        assertEquals("medium", validationForm.getPerceivedRisk());
        assertEquals(3, validationForm.getCertaintyLevel());
        assertEquals("newComment", validationForm.getComment());
        assertEquals(user, validationForm.getUser());
        assertFalse(validationForm.isPublic());

    }


}
