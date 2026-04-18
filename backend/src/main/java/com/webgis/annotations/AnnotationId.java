package com.webgis.annotations;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class AnnotationId implements Serializable  {
    private Long userId;
    private Long mapId;
    private String division;

    public AnnotationId(Long userId, Long mapId, String division) {
        this.userId = userId;
        this.mapId= mapId;
        this.division = division;
    }

    public AnnotationId(){}

    public Long getMapId(){return this.mapId;}
    public Long getUserId(){return this.userId;}
    public String getDivision(){return this.division;}

    public void setMapId(Long mapId){this.mapId = mapId;}
    public void setUserId(Long userId){this.userId = userId;}
    public void setDivision(String division){this.division = division;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnotationId)) return false;

        final AnnotationId annotationId = (AnnotationId) o;

        if (mapId != annotationId.mapId){
            return false;
        }
        if (userId != annotationId.userId) {
            return false;
        }
        return Objects.equals(division, annotationId.division);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, mapId, division);
    }


}
