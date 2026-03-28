package com.webgis.evaluationform;

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
class EvaluationFormServiceTest {

    @Mock
    private EvaluationFormRepository evaluationFormRepository;

    @InjectMocks
    private EvaluationFormService evaluationFormService;

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

        EvaluationForm evaluationForm = new EvaluationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                finalMap,
                true
        );
        evaluationForm.setId(1);

        when(evaluationFormService.findFormById(evaluationForm.getId())).thenReturn(Optional.of(evaluationForm));
        when(evaluationFormRepository.save(evaluationForm)).thenReturn(evaluationForm);

        //Act
        EvaluationForm updatedEvaluationForm = evaluationFormService.updateForm(
                evaluationForm.getId(),
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
        assertEquals("Mfoundi", updatedEvaluationForm.getDivision());
        assertEquals(4, updatedEvaluationForm.getAgreementLevel());
        assertEquals("medium", updatedEvaluationForm.getPerceivedRisk());
        assertEquals(3, updatedEvaluationForm.getCertaintyLevel());
        assertEquals("newComment", updatedEvaluationForm.getComment());
        assertEquals(user, updatedEvaluationForm.getUser());
        assertEquals(finalMap, updatedEvaluationForm.getFinalMap());
        assertFalse(updatedEvaluationForm.isPublic());

    }


}
