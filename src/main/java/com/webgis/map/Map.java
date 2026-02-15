package com.webgis.map;

import jakarta.persistence.*;

@Entity
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="BYTEA")
    private byte[] zipFile;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="BYTEA")
    private byte[] fileGeoJson;

    public Map(){}

    public Map(String name,
               byte[] zipFile,
               byte[] fileGeoJson){
        this.name=name;
        this.zipFile = zipFile;
        this.fileGeoJson = fileGeoJson;
    }

    /* ***************************************
     * **************** GETTER ***************
     * ****************************************/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {this.name = name;}

    public void setZipFile(byte[] zipFile) {
        this.zipFile = zipFile;
    }


    public void setFileGeoJson(byte[] fileGeoJson) {
        this.fileGeoJson = fileGeoJson;
    }
}

