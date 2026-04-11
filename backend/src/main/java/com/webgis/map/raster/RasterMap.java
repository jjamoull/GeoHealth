package com.webgis.map.raster;


import com.webgis.map.finalmap.FinalMap;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "rasterMap")
public class RasterMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_map_id", nullable = true)
    private FinalMap finalMap;

    public RasterMap(){}

    public RasterMap(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Long getId(){return id; }

    public String getTitle() {return title; }

    public String getDescription() { return description;}

    public FinalMap getFinalMap() { return finalMap; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setFinalMap(FinalMap finalMap) {
        this.finalMap = finalMap;
    }
}

