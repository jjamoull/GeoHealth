package com.webgis.annotations;

import com.webgis.MessageDto;
import com.webgis.annotations.dto.AnnotationDTO;
import com.webgis.annotations.dto.AnnotationPostDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/annotation")

public class AnnotationController {
    final private AnnotationService annotationService;

    public AnnotationController(AnnotationService annotationService){this.annotationService  = annotationService;}

    @GetMapping("/{mapId}/{userId}/{division}")
    public ResponseEntity<Object> getAnnotation(@PathVariable Long mapId,
                                                @PathVariable Long userId,
                                                @PathVariable String division){

        final Optional<Annotation> annotation = annotationService.findByAnnotationId(new AnnotationId(mapId, userId, division));
        if (annotation.isPresent()){

            return ResponseEntity.status(200).body(new AnnotationDTO( annotation.get().getGeoJson()));
        }else {
            return ResponseEntity.status(500).body("Geojson for annotations not found");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> postAnnotation(@RequestBody AnnotationPostDTO dto) {
        try {
            Long mapId = dto.mapId();
            Long userId = dto.userId();
            String division = dto.division();
            String geoJson = dto.geoJson();

            AnnotationId id = new AnnotationId(userId, mapId, division);

            Annotation annotation = new Annotation(id, geoJson);

            return ResponseEntity.status(200).body(annotationService.save(annotation));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageDto(e.getMessage()));
        }
    }

}
