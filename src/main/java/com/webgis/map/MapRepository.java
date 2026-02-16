package com.webgis.map;

import com.webgis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<Map, Integer> {
    Optional<Map> findByTitle(String title);
    Optional<Map> findById(long id);
}
