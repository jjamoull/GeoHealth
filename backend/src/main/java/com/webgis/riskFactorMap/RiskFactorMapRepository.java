package com.webgis.riskFactorMap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiskFactorMapRepository extends JpaRepository<RiskFactorMap, Integer> {
    Optional<RiskFactorMap> findById(long id);
}
