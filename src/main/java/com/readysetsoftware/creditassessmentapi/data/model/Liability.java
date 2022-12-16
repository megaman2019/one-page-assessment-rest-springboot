package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vBorrower_Liabilities")
public class Liability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iLiabilityID")
    private Integer id;

    @Column(name = "cLiabilityType")
    private String liabilityType;

    @Column(name = "fLiabilityAmount")
    private Float liabilityAmount;

    @Column(name = "fLiabilityOwing")
    private Float liabilityOwing;

    @Column(name = "fLiabilityPerMonth")
    private Float liabilityPerMonth;

    @Column(name = "fAdjustment")
    private Float adjustment;

    @Column(name = "fCorrection")
    private Float correction;

    @Column(name = "fDifference")
    private Float difference;

    @Column(name = "fManualTotal")
    private Float manualTotal;

    @Column(name = "fPercentage")
    private Float percentage;

    @Column(name = "cPayoutOnSettlement")
    private String payoutOnSettlement;
//
//    @Column(name = "cNotes")
//    private String notes;

    @Column(name = "cInstitution")
    private String institution;

    @Column(name = "cAccountNumber")
    private String accountNo;

    @Column(name = "bIsinArrearsorOverLimit")
    private Boolean arrearsOrOverlimit;

    @Column(name = "iCurrentIntrimStatement")
    private Integer statementWithinPolicy;

    @ManyToOne
    @JoinColumn(name="iBorrowerID")
    @JsonIgnoreProperties("liabilities")
    private Borrower borrower;


}
