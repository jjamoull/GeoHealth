package com.webgis.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition="BYTEA")
    private byte[] fileShp;

    @Lob
    @Column(columnDefinition="BYTEA")
    private byte[] fileShx;

    @Lob
    @Column(columnDefinition="BYTEA")
    private byte[] filePrj;

    @Lob
    @Column(columnDefinition="BYTEA")
    private byte[] fileDbf;

    @Lob
    @Column(columnDefinition="BYTEA")
    private byte[] fileCpg;

    @Lob
    @JsonProperty(value = "null")
    @Column(columnDefinition="BYTEA")
    private byte[] fileGeoJson;

    public Map(){}

    public Map(String name,
               byte[] fileShp,
               byte[] fileShx,
               byte[] filePrj,
               byte[] fileDbf,
               byte[] fileCpg,
               byte[] fileGeoJson){
        this.name=name;
        this.fileShp = fileShp;
        this.fileShx = fileShx;
        this.filePrj = filePrj;
        this.fileDbf = fileDbf;
        this.fileCpg = fileCpg;
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

    public byte[] getFileShp() {
        return fileShp;
    }

    public byte[] getFileShx() {
        return fileShx;
    }

    public byte[] getFilePrj() {
        return filePrj;
    }

    public byte[] getFileDbf() {
        return fileDbf;
    }

    public byte[] getFileCpg(){return fileCpg;}

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

    public void setFileShp(byte[] fileShp) {
        this.fileShp = fileShp;
    }

    public void setFileShx(byte[] fileShx) {
        this.fileShx = fileShx;
    }

    public void setFilePrj(byte[] filePrj) {
        this.filePrj = filePrj;
    }

    public void setFileDbf(byte[] fileDbf) {
        this.fileDbf = fileDbf;
    }

    public void setFileCpg(byte[] fileCpg) {
        this.fileCpg = fileCpg;
    }

    public void setFileGeoJson(byte[] fileGeoJson) {
        this.fileGeoJson = fileGeoJson;
    }
}

