package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vAppDLAProfile")
public class AppDLAProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppID")
    private Integer id;

    @Column(name = "cFinancier")
    private String financier;

    @Column(name = "cProfileName")
    private String profileName;

    @Column(name = "fApprovalLimit")
    private Float approvalLimit;

    @Column(name = "cFirstName")
    private String firstname;

    @Column(name = "cSurname")
    private String surname;

    @OneToOne
    @JoinColumn(name="iAppID", insertable=false, updatable=false)
    @JsonIgnoreProperties("appDLAProfile")
    private Application application;

}
