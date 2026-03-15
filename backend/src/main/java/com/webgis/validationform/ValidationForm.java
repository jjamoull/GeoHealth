package com.webgis.validationform;

import com.webgis.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="validationForms")
public class ValidationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String department;

    @Column
    private int agreementLevel;

    @Column
    private String perceivedRisk;

    @Column
    private int certaintyLevel;

    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ValidationForm(){}

    public ValidationForm(
            String department,
            int agreementLevel,
            String perceivedRisk,
            int certaintyLevel,
            String comment,
            User user){
        this.department=department;
        this.agreementLevel=agreementLevel;
        this.perceivedRisk=perceivedRisk;
        this.certaintyLevel=certaintyLevel;
        this.comment=comment;
        this.user=user;
    }

    public Long getId() {return id;}

    public String getDepartment() {return department;}

    public int getAgreementLevel() {return agreementLevel;}

    public String getPerceivedRisk() {return perceivedRisk;}

    public int getCertaintyLevel() {return certaintyLevel;}

    public String getComment() {return comment;}

    public User getUser(){return user;}

    public void setId(long id){this.id=id;}

    public void setDepartment(String department) {this.department = department;}

    public void setAgreementLevel(int agreementLevel) {this.agreementLevel = agreementLevel;}

    public void setPerceivedRisk(String perceivedRisk) {this.perceivedRisk = perceivedRisk;}

    public void setCertaintyLevel(int certaintyLevel){this.certaintyLevel=certaintyLevel;}

    public void setComment(String comment) {this.comment = comment;}

    public void setUser(User user) {this.user = user;}
}
