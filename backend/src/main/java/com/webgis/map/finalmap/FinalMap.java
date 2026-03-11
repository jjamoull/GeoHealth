package com.webgis.map.finalmap;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;

@Entity
@Table(name = "maps")
public class FinalMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="BYTEA")
    private byte[] zipFile;

    @Column(name = "fileGeoJson", columnDefinition = "TEXT")
    private String fileGeoJson;

    public FinalMap(){}

    public FinalMap(String title,
                    String description,
                    byte[] zipFile,
                    String fileGeoJson){
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

    public String getFileGeoJson() {
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


    public void setFileGeoJson(String fileGeoJson) {
        this.fileGeoJson = fileGeoJson;
    }
}

