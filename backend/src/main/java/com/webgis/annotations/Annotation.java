package com.webgis.annotations;

import com.webgis.map.tile.TileId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "annotation")
public class Annotation {
    @EmbeddedId
    private AnnotationId annotationId;

    @Column(name = "geo_json", columnDefinition = "TEXT")
    private String geoJson;

    public Annotation(){}

    public Annotation(AnnotationId annotationId,
                      String geoJson){
        this.annotationId = annotationId;
        this.geoJson = geoJson;
    }

    public AnnotationId getAnnotationId(){
        return this.annotationId;
    }

    public String getGeoJson(){
        return this.geoJson;
    }

    public void setAnnotationId(AnnotationId annotationId){this.annotationId = annotationId;}

    public void setGeoJson(String geoJson){this.geoJson = geoJson;}


}
