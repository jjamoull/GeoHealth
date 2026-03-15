package com.webgis.validationform;

import com.webgis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValidationFormRepository extends JpaRepository<ValidationForm,Integer> {
    Optional<ValidationForm> findById(long id);
    List<ValidationForm> findByUser(User user);
    List<ValidationForm> findByDepartment(String department);
}
