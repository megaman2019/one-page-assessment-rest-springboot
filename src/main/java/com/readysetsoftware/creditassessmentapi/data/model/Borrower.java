package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vBorrowers")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iBorrowerID")
    private Integer id;

    @Column(name = "iCustID")
    private Integer custId;

    @Column(name = "cFirstName")
    private String firstName;

    @Column(name = "cMiddleName")
    private String middleName;

    @Column(name = "cSurname")
    private String surname;

    @Column(name = "bPrimary")
    private Boolean primary;

    @Column(name = "bCorporate")
    private Boolean corporate;

    @Column(name = "cMaritalStatus")
    private String maritalStatus;

    @Column(name = "dDOB")
    private Date dateOfBirth;

    @Column(name = "iDependents")
    private Integer totalDependents;

    @Column(name = "cCurrentAddress")
    private String address;

    @Column(name = "cCurrentEmpName")
    private String currentEmpName;

    @Column(name = "cOccupation")
    private String currentOccupation;

    @Column(name = "cCurrentEmpABN")
    private String currentAbn;

    @Column(name = "fCurrentEmpIncome")
    private Float currentEmpIncome;

    @Column(name = "fCurrentEmpNetIncome")
    private Float currentEmpNetIncome;

    @Column(name = "cCurrentEmploymentType")
    private String currentEmpType;

    @Column(name = "iCurrentEmpYears")
    private Integer currentEmpYears;

    @Column(name = "iCurrentEmpMonths")
    private Integer currentEmpMonths;

    @Column(name = "cPreviousEmpName")
    private String previousEmpName;

    @Column(name = "cPrevOccupation")
    private String previousOccupation;

    @Column(name = "fPreviousEmpIncome")
    private Float previousEmpIncome;

    @Column(name = "fPreviousEmpNetIncome")
    private Float previousEmpNetIncome;

    @Column(name = "cPreviousEmploymentType")
    private String previousEmploymentType;

    @Column(name = "iPreviousEmpYears")
    private Integer previousEmpYears;

    @Column(name = "bGuarantor")
    private Boolean guarantor;

    @Column(name = "cResidencyStatus")
    private String residencyStatus;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("borrowers")
    private Application application;

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    private List<AssessmentResult> assessmentResults;

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    private List<Asset> assets;

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    private List<Liability> liabilities;

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    private List<Income> incomes;

    @OneToMany(mappedBy = "borrower")
    @JsonIgnoreProperties("borrower")
    private List<Identification> identifications;

//    @OneToMany(mappedBy = "borrower", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("borrower")
//    private List<CreditHistory> creditHistories;

//    private List<CreditHistory> creditHistories() {
//        return
//    }

}
