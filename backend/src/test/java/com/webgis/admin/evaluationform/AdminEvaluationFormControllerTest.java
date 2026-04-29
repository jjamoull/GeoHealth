package com.webgis.admin.evaluationform;

import com.webgis.MessageDto;
import com.webgis.admin.dto.evaluationform.AdminResponseEvaluationFormDto;
import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormService;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapService;
import com.webgis.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AdminEvaluationFormControllerTest {

    @Mock
    private EvaluationFormService evaluationFormService;

    @Mock
    private FinalMapService finalMapService;

    @InjectMocks
    private AdminEvaluationFromController adminEvaluationFromController;

    private User user;
    private FinalMap finalMap;
    private EvaluationForm evaluationForm;

    @BeforeEach
    void SetUp(){
        user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.com",
                "password",
                "Admin");
        user.setId(1L);

        byte[] dataZip ={66};
        List<String> list = new ArrayList<>(List.of("dry"));
        finalMap = new FinalMap("title",
                "risk map",
                list,
                dataZip,
                "file");
        finalMap.setId(1L);

        evaluationForm = new EvaluationForm(
                "Djerem",
                4,
                "High",
               4,
                "comment",
                user,
                finalMap,
                true
                );
        evaluationForm.setId(1L);
    }

    // getAllFormForFinalMap Test
    @Test
    void getAllFormForFinalMapShouldReturnNotFoundWhenMapDoesNotExistsTest(){

        //Arrange
        when(finalMapService.findById(1L)).thenReturn(Optional.empty());

        //Act
        ResponseEntity<Object> response = adminEvaluationFromController.getAllFormForFinalMap(finalMap.getId());


        //Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllFormForFinalMapShouldReturnOkAndTheListOfMapTest(){
        //Arrange
        when(finalMapService.findById(1L)).thenReturn(Optional.of(finalMap));
        when(evaluationFormService.getAllFormForFinalMap(finalMap)).thenReturn(List.of(evaluationForm));

        //Act
        ResponseEntity<Object> response = adminEvaluationFromController.getAllFormForFinalMap(finalMap.getId());


        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<AdminResponseEvaluationFormDto> body =
                (List<AdminResponseEvaluationFormDto>) response.getBody();
        assertEquals(1, body.size());
        AdminResponseEvaluationFormDto adminEvaluationFormTocheck = body.get(0);
        assertDtoMatchesEntity(adminEvaluationFormTocheck,evaluationForm);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getAllFormForFinalMapShouldReturnOkAndTheListOfMapWithDeletedUserTest(){
        //Arrange
        when(finalMapService.findById(1L)).thenReturn(Optional.of(finalMap));
        when(evaluationFormService.getAllFormForFinalMap(finalMap)).thenReturn(List.of(evaluationForm));
        user.setDeleted(true);

        //Act
        ResponseEntity<Object> response = adminEvaluationFromController.getAllFormForFinalMap(finalMap.getId());


        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<AdminResponseEvaluationFormDto> body =
                (List<AdminResponseEvaluationFormDto>) response.getBody();
        assertEquals(1, body.size());
        AdminResponseEvaluationFormDto adminEvaluationFormTocheck = body.get(0);
        assertDtoMatchesEntity(adminEvaluationFormTocheck,evaluationForm);
    }

    //deleteForm Test
    @Test
    void deleteFormShouldReturnNotFoundFormDoesNotExistsTest(){
        //Arrange
        when(evaluationFormService.findFormById(1L)).thenReturn(Optional.empty());

        //Act
        ResponseEntity<MessageDto> response = adminEvaluationFromController.deleteForm(evaluationForm.getId());

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteFormShouldReturnOkAndCallDeleteService(){
        //Arrange
        when(evaluationFormService.findFormById(1L)).thenReturn(Optional.of(evaluationForm));

        //Act
        ResponseEntity<MessageDto> response = adminEvaluationFromController.deleteForm(evaluationForm.getId());

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(evaluationFormService).deleteForm(1L,user);
    }


    //Test Helper

    /**
     * Check that the content of the AdminResponseEvaluationFormDto match the evaluationForm entity one
     *
     * @param dto the dto you want to check
     * @param entity the evaluationForm entity that it should match
     */
    private void assertDtoMatchesEntity(
            AdminResponseEvaluationFormDto dto,
            EvaluationForm entity
    ) {
        assertNotNull(dto);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getDivision(), dto.division());
        assertEquals(entity.getAgreementLevel(), dto.agreementLevel());
        assertEquals(entity.getPerceivedRisk(), dto.perceivedRisk());
        assertEquals(entity.getCertaintyLevel(), dto.certaintyLevel());
        assertEquals(entity.getComment(), dto.comment());
        assertEquals(entity.isPublic(), dto.isPublic());

        if (entity.getUser().isDeleted()) {
            assertEquals("Deleted user", dto.username());
            assertEquals(" ", dto.firstName());
            assertEquals(" ", dto.lastName());
        } else {
            assertEquals(entity.getUser().getUsername(), dto.username());
            assertEquals(entity.getUser().getFirstName(), dto.firstName());
            assertEquals(entity.getUser().getLastName(), dto.lastName());
        }
    }

}
