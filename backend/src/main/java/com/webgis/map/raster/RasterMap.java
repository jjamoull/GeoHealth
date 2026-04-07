package com.webgis.map.raster;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "rasterMap")
public class RasterMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String typeOfRaster;

    public RasterMap(){}

    public RasterMap(String title, String description, String typeOfRaster){
        this.title = title;
        this.description = description;
        this.typeOfRaster = typeOfRaster;
    }

    public Long getId(){return id; }

    public String getTitle() {return title; }

    public String getDescription() { return description;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}

