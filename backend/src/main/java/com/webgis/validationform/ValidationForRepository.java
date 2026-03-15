package com.webgis.validationform;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationForRepository extends JpaRepository<ValidationForm,Integer> {
    Optional<ValidationForm> findById(long id);
}
