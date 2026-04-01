package com.webgis.evaluationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EvaluationFormRepository extends JpaRepository<EvaluationForm,Integer> {
    Optional<EvaluationForm> findById(long id);

    List<EvaluationForm> findByFinalMap(FinalMap finalMap);

    List<EvaluationForm>
    findByFinalMapAndDivisionAndPerceivedRiskIsNotNullAndCertaintyLevelIsNotNullAndIsPublicTrue(
            FinalMap
            finalMap, String division);

    Optional<EvaluationForm> findByUserAndDivisionAndFinalMap(User user, String division, FinalMap finalMap);

    boolean existsByUserAndDivisionAndFinalMap(User user, String division, FinalMap finalMap);

    //Manual Query has it is not possible directly with jpa function
    @Query("""
    SELECT DISTINCT e.division
    FROM EvaluationForm e
    WHERE e.isPublic = true
    """)
    List<String> findDivisionsWithPublicEvaluationForms();
}
