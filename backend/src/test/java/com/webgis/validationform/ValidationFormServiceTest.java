package com.webgis.validationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationFormServiceTest {

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

        byte[] dataZip ={66};
        FinalMap finalMap = new FinalMap(
                "title",
                "risk map",
                dataZip,
                "file");

        ValidationForm validationForm= new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                finalMap,
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
                finalMap,
                false
        );

        //Assert
        assertEquals("Mfoundi", updatedValidationForm.getDivision());
        assertEquals(4, updatedValidationForm.getAgreementLevel());
        assertEquals("medium", updatedValidationForm.getPerceivedRisk());
        assertEquals(3, updatedValidationForm.getCertaintyLevel());
        assertEquals("newComment", updatedValidationForm.getComment());
        assertEquals(user, updatedValidationForm.getUser());
        assertEquals(finalMap, updatedValidationForm.getFinalMap());
        assertFalse(updatedValidationForm.isPublic());

    }


}
