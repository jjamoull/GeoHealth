package com.webgis.map.finalrastermap;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FinalRasterMapRepository extends JpaRepository<FinalRasterMap, Integer> {
    Optional<FinalRasterMap> findByTitle(String title);
    Optional<FinalRasterMap> findById(long id);
}
