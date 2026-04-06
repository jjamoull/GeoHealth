package com.webgis.measure;

import com.webgis.evaluationform.EvaluationForm;
import com.webgis.evaluationform.EvaluationFormRepository;
import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasureServiceTest {

    @Mock
    private EvaluationFormRepository evaluationFormRepository;

    @InjectMocks
    private MeasureService measureService;

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

        finalMap = new FinalMap(
                "title",
                "risk map",
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
                        0,
                        "comment",
                        user3,
                        finalMap,
                        true
                )
        );
    }

    @Test
    void computeWeightedEntropyForADivisionTest() {

        // Arrange
        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
            ).thenReturn(evaluationForms);

        // Act
        double result = measureService.computeWeightedEntropyForADivision(finalMap,"Wouri", "low");

        // Assert
        assertEquals(0.5, result);
    }

    @Test
    void  computeGlobalConsensusIndexTest() {

        // Arrange
        Map<String,String> riskEvaluationMap = new HashMap<>();
        riskEvaluationMap.put("Wouri", "low");

        when(evaluationFormRepository.findDivisionsWithPublicEvaluationForms()
        ).thenReturn(List.of("Wouri"));

        when(evaluationFormRepository
                .findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
                        finalMap,
                        "Wouri")
        ).thenReturn(evaluationForms);

        // Act
        double result = measureService.computeGlobalConsensusIndex(finalMap,riskEvaluationMap);

        // Assert
        assertEquals(0.5, result);
    }

    @Test
    void buildKrippendorffHashMapTest(){
        //Act
        Map<Long, Map<String, Integer>> krippendorffHashMap= measureService.buildKrippendorffHashMap(evaluationForms);

        //Assert
        assertEquals(3, krippendorffHashMap.size());

        assertTrue(krippendorffHashMap.get(1L).containsKey("Wouri"));
        assertTrue(krippendorffHashMap.get(2L).containsKey("Wouri"));
        assertTrue(krippendorffHashMap.get(3L).containsKey("Wouri"));


        assertEquals(1,krippendorffHashMap.get(1L).get("Wouri"));
        assertEquals(2, krippendorffHashMap.get(2L).get("Wouri"));
        assertEquals(1,krippendorffHashMap.get(3L).get("Wouri"));

    }


    @Test
    void buildKrippendorffHashMapEmptyTest(){
        //Arrange
        List<EvaluationForm> emptyEvaluationFromsList= new ArrayList<>();

        //Act
        Map<Long, Map<String, Integer>> krippendorffHashMap= measureService.buildKrippendorffHashMap(emptyEvaluationFromsList);

        //Assert
        assertEquals(0, krippendorffHashMap.size());
    }


}
