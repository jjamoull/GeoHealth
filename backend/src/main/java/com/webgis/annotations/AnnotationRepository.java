package com.webgis.annotations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AnnotationRepository  extends JpaRepository<Annotation, AnnotationId> {
    Optional<Annotation> findByAnnotationId(AnnotationId annotationId);
}

