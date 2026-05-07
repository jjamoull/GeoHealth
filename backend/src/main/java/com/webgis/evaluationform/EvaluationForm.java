package com.webgis.evaluationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="evaluationForms", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "division", "final_map_id"})})
public class EvaluationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String division;

    @Column
    private Integer agreementLevel;

    @Column
    private String perceivedRisk;

    @Column
    private Integer certaintyLevel;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_map_id", nullable = false)
    private FinalMap finalMap;

    @Column(nullable = false)
    private boolean isPublic;

    public EvaluationForm(){}

    public EvaluationForm(
            String division,
            Integer agreementLevel,
            String perceivedRisk,
            Integer certaintyLevel,
            String comment,
            User user,
            FinalMap finalMap,
            boolean isPublic){
        this.division =division;
        this.agreementLevel=agreementLevel;
        this.perceivedRisk=perceivedRisk;
        this.certaintyLevel=certaintyLevel;
        this.comment=comment;
        this.user=user;
        this.finalMap=finalMap;
        this.isPublic=isPublic;
    }

    public long getId() {return id;}

    public String getDivision() {return division;}

    public Integer getAgreementLevel() {return agreementLevel;}

    public String getPerceivedRisk() {return perceivedRisk;}

    public Integer getCertaintyLevel() {return certaintyLevel;}

    public String getComment() {return comment;}

    public User getUser(){return user;}

    public FinalMap getFinalMap(){return finalMap;}

    public boolean isPublic(){return isPublic;}


    public void setId(long id){this.id=id;}

    public void setDivision(String division) {this.division = division;}

    public void setAgreementLevel(Integer agreementLevel) {this.agreementLevel = agreementLevel;}

    public void setPerceivedRisk(String perceivedRisk) {this.perceivedRisk = perceivedRisk;}

    public void setCertaintyLevel(Integer certaintyLevel){this.certaintyLevel=certaintyLevel;}

    public void setComment(String comment) {this.comment = comment;}

    public void setUser(User user) {this.user = user;}

    public void setFinalMap(FinalMap finalMap){this.finalMap=finalMap;}

    public void setIsPublic(boolean isPublic){this.isPublic=isPublic;}
}
