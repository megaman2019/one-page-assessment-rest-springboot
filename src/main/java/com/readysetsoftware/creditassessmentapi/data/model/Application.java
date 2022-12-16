package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.readysetsoftware.creditassessmentapi.data.model.conditions.ApplicationCondition;
import com.readysetsoftware.creditassessmentapi.data.model.conditions.ApplicationSpecialCondition;
import com.readysetsoftware.creditassessmentapi.data.model.conditions.SettlementCondition;
import com.readysetsoftware.creditassessmentapi.data.model.conditions.SettlementSpecialCondition;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vAppMain")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppID")
    private Integer id;

    @Column(name = "cLoanName")
    private String loanName;

    @Column(name = "fFinalLVR")
    private Float finalLVR;

    @Column(name = "fFundsPositionTotal")
    private Float fundsPositionTotal;

    @Column(name = "fFundsRequiredTotal")
    private Float fundsRequiredTotal;

    @Column(name = "fSurplusTotal")
    private Float surplusTotal;

    @Column(name = "fMortgageInsurerPremium")
    private Float mortgageInsurerPremium;

    @Column(name = "cInsurerName")
    private String insurerName;

    @Column(name = "cLoanPurpose")
    private String loanPurpose;

    @Column(name = "iNumberOfHouseholds")
    private Integer totalHouseholds;

    @Column(name = "cOwner")
    private String owner;

    @Column(name = "cBranch")
    private String branch;

    @Column(name = "cSalesConsultant")
    private String salesConsultant;

    @Column(name = "cConsultantBranch")
    private String consultantBranch;

    @Column(name = "cConsultantRANNo")
    private String consultantRANNo;

    private Boolean isLocked;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<Borrower> borrowers;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<Mortgagor> mortgagors;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<Security> securities;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<Product> products;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<Note> notes;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<ApplicationCondition> applicationConditions;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<ApplicationSpecialCondition> applicationSpecialConditions;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<SettlementCondition> settlementConditions;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<SettlementSpecialCondition> settlementSpecialConditions;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<FundsPosition> fundsPositions;

    @OneToMany(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private List<RolesAppOwner> rolesAppOwners;

    @OneToOne(mappedBy = "application")
    @JsonIgnoreProperties("application")
    private AppDLAProfile appDLAProfile;

}
