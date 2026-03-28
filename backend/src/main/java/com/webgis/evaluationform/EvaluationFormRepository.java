package com.webgis.evaluationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluationFormRepository extends JpaRepository<EvaluationForm,Integer> {
    Optional<EvaluationForm> findById(long id);
    List<EvaluationForm> findByFinalMap(FinalMap finalMap);
    Optional<EvaluationForm> findByUserAndDivisionAndFinalMap(User user, String division, FinalMap finalMap);
    boolean existsByUserAndDivisionAndFinalMap(User user, String division, FinalMap finalMap);
}
