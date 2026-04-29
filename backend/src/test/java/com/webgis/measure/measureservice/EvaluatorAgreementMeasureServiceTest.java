package com.webgis.measure.measureservice;

import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.MapTag;
import com.webgis.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.webgis.map.finalmap.MapTag.EBOLA;
import static com.webgis.map.finalmap.MapTag.DRY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluatorAgreementMeasureServiceTest {

    @Mock
    private EvaluationFormRepository evaluationFormRepository;

    @InjectMocks
    private EvaluatorAgreementMeasureService evaluatorAgreementMeasureService;


    private User user1;
    private User user2;
    private User user3;

    private FinalMap finalMap;

    private List<EvaluationForm> evaluationFormsWouri;
    private List<EvaluationForm> evaluationFormsMbam;

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

        evaluationFormsWouri = List.of(
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
                        0,
                        "comment",
                        user3,
                        finalMap,
                        true
                )
        );


        evaluationFormsMbam= List.of(
                new EvaluationForm(
                        "Mbam",
                        4,
                        "High",
                        4,
                        "comment",
                        user1,
                        finalMap,
                        true
                ),
                new EvaluationForm(
                        "Mbam",
                        3,
                        "High",
                        3,
                        "comment",
                        user2,
                        finalMap,
                        true
                )
        );
    }

    @Test
    void computeDivisionalConsensusScoreTest(){
        //Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationFormsWouri);

        //Act
        double result= evaluatorAgreementMeasureService.computeDivisionalConsensusScore(finalMap,"Wouri");

        //Assert
        assertEquals(0.3690702464285426,result);
    }

    @Test
    void computeNationalConsensusScoreTest(){
        //Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationFormsWouri);

        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Mbam")
        ).thenReturn(evaluationFormsMbam);

        when(evaluationFormRepository
                .findDivisionsWithValidPublicEvaluationForms(finalMap)
        ).thenReturn(List.of("Mbam","Wouri"));

        //Act
        double result= evaluatorAgreementMeasureService.computeNationalConsensusScore(finalMap);

        //Assert
        assertEquals(0.6845351232142713,result);
    }
}
