package com.webgis.riskFactorMap;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "riskFactorMap")
public class RiskFactorMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;


    public RiskFactorMap(){}

    public RiskFactorMap(String title,
                         String description){
        this.title = title;
        this.description = description;
    }

    /* ***************************************
     * **************** GETTER ***************
     * ****************************************/

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public long getId(){return this.id;}
    /** ***************************************
     * *************** SETTER *****************
     * **************************************** */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTileDescription(String description){
        this.description = description;
    }
}

