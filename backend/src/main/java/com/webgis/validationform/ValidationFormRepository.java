package com.webgis.validationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValidationFormRepository extends JpaRepository<ValidationForm,Integer> {
    Optional<ValidationForm> findById(long id);
    List<ValidationForm> findByFinalMap(FinalMap finalMap);
    Optional<ValidationForm> findByUserAndDepartmentAndFinalMap(User user,String department,FinalMap finalMap);
    List<ValidationForm> findByDepartmentAndFinalMap(String department,FinalMap finalMap);
    boolean existsByUserAndDepartmentAndFinalMap(User user, String department, FinalMap finalMap);
}
