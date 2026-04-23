package com.webgis.measure.measureservice;

import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.measure.RiskLevel;
import com.webgis.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeanMesureServiceTest {

    @Mock
    private EvaluationFormRepository evaluationFormRepository;

    @InjectMocks
    private MeanMesureService meanMesureService;


    private User user1;
    private User user2;
    private User user3;

    private FinalMap finalMap;

    private List<EvaluationForm> evaluationForms;

    @BeforeEach
    void setUp() {

        user1 = new User(
                "pseudo1",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password1",
                "Admin"
        );
        user1.setId(1L);

        user2 = new User(
                "pseudo2",
                "Thomas",
                "Busino",
                "thomas.busino@outlook.be",
                "password2",
                "USER"
        );
        user2.setId(2L);

        user3 = new User(
                "pseudo3",
                "Houria",
                "Houria",
                "houria.douglas@yahoo.fr",
                "password3",
                "Admin"
        );
        user3.setId(3L);

        byte[] dataZip = {66};
        List<String> list = new ArrayList<>(List.of("dry"));
        finalMap = new FinalMap(
                "title",
                "risk map",
                list,
                dataZip,
                "file"
        );

        evaluationForms = List.of(
                new EvaluationForm(
                        "Wouri",
                        2,
                        "low",
                        4,
                        "comment",
                        user1,
                        finalMap,
                        true
                ),
                new EvaluationForm(
                        "Wouri",
                        2,
                        "medium",
                        4,
                        "comment",
                        user2,
                        finalMap,
                        true
                ),
                new EvaluationForm(
                        "Wouri",
                        2,
                        "low",
                        4,
                        "comment",
                        user3,
                        finalMap,
                        true
                )
        );
    }

    @Test
    void computeMeanDivisionalAgreementScoreTest(){
        // Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndAgreementLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationForms);

        //Act
        Double result = meanMesureService.computeMeanDivisionalAgreementScore(finalMap,"Wouri");

        //Assert
        assertEquals(2,result);
    }

    @Test
    void computeMeanCertaintyForMapForDivisionTest(){
        // Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationForms);

        //Act
        Double result = meanMesureService.computeMeanCertaintyForMapForDivision(finalMap,"Wouri");

        //Assert
        assertEquals(4,result);
    }

    @Test
    void computeDominantPerceivedRiskLevelForMapForDivision(){
        //Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationForms);

        //Act
        RiskLevel result = meanMesureService.computeDominantPerceivedRiskLevelForMapForDivision(finalMap,"Wouri");

        //Assert
        assertEquals(RiskLevel.LOW,result);
    }



}
