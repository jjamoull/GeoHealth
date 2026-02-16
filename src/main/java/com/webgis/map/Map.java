package com.webgis.map;

import jakarta.persistence.*;

@Entity
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="BYTEA")
    private byte[] zipFile;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="BYTEA")
    private byte[] fileGeoJson;

    public Map(){}

    public Map(String title,
               String description,
               byte[] zipFile,
               byte[] fileGeoJson){
        this.title=title;
        this.description =description;
        this.zipFile = zipFile;
        this.fileGeoJson = fileGeoJson;
    }

    /* ***************************************
     * **************** GETTER ***************
     * ****************************************/
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getZipFile(){return zipFile;}

    public byte[] getFileGeoJson() {
        return fileGeoJson;
    }

    /** ***************************************
     * *************** SETTER *****************
     * **************************************** */
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description){
        this.description = description;
    }

    public void setZipFile(byte[] zipFile) {
        this.zipFile = zipFile;
    }


    public void setFileGeoJson(byte[] fileGeoJson) {
        this.fileGeoJson = fileGeoJson;
    }
}

