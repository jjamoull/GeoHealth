package com.webgis.map.raster;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RasterMapRepository extends JpaRepository<RasterMap, Integer> {
    Optional<RasterMap> findById(long id);
    List<RasterMap> findByFinalMapIsNull();
    List<RasterMap> findByFinalMapIsNotNull();
}
