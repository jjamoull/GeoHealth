package com.webgis.evaluationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationFormEntityTest {

    @Test
    void getterTest(){
        //Arrange
        User user = new User("pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin");

        byte[] dataZip ={66};
        FinalMap finalMap = new FinalMap(
                "title",
                "risk map",
                dataZip,
                "file");

        EvaluationForm evaluationForm =new EvaluationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                finalMap,
                true
        );

        //Assert
        assertEquals("Wouri", evaluationForm.getDivision());
        assertEquals(2, evaluationForm.getAgreementLevel());
        assertEquals("low", evaluationForm.getPerceivedRisk());
        assertEquals(4, evaluationForm.getCertaintyLevel());
        assertEquals("comment", evaluationForm.getComment());
        assertEquals(user, evaluationForm.getUser());
        assertEquals(finalMap, evaluationForm.getFinalMap());
        assertTrue(evaluationForm.isPublic());

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

        byte[] dataZip1 ={66};
        FinalMap finalMap1 = new FinalMap(
                "title",
                "risk map",
                dataZip1,
                "file");

        byte[] dataZip2 ={90};
        FinalMap finalMap2 = new FinalMap(
                "title",
                "risk map",
                dataZip2,
                "file");

        EvaluationForm evaluationForm = new EvaluationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user1,
                finalMap1,
                true
        );

        // Act
        evaluationForm.setDivision("Mfoundi");
        evaluationForm.setAgreementLevel(4);
        evaluationForm.setPerceivedRisk("high");
        evaluationForm.setCertaintyLevel(1);
        evaluationForm.setComment("new comment");
        evaluationForm.setUser(user2);
        evaluationForm.setFinalMap(finalMap2);
        evaluationForm.setIsPublic(false);

        // Assert
        assertEquals("Mfoundi", evaluationForm.getDivision());
        assertEquals(4, evaluationForm.getAgreementLevel());
        assertEquals("high", evaluationForm.getPerceivedRisk());
        assertEquals(1, evaluationForm.getCertaintyLevel());
        assertEquals("new comment", evaluationForm.getComment());
        assertEquals(user2, evaluationForm.getUser());
        assertEquals(finalMap2, evaluationForm.getFinalMap());
        assertFalse(evaluationForm.isPublic());
    }
}
