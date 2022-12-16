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
@Table(name ="vBorrower_Incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iIncomeID")
    private Integer id;

    @Column(name = "cIncomeType")
    private String incomeType;

    @Column(name = "cIncomePeriod")
    private String incomePeriod;

    @Column(name = "fAmount")
    private Float amount;

    @Column(name = "fNetAmount")
    private Float netAmount;

    @Column(name = "fAdjustment")
    private Float adjustment;

    @Column(name = "fCorrection")
    private Float correction;

    @Column(name = "fDifference")
    private Float difference;

    @Column(name = "fManualTotal")
    private Float manualTotal;

    @Column(name = "cReason")
    private String reason;
//
//    @Column(name = "cDetails")
//    private String notes;

    @Column(name = "fPercentage")
    private Float ownedPercentage;

    @ManyToOne
    @JoinColumn(name="iBorrowerID")
    @JsonIgnoreProperties("incomes")
    private Borrower borrower;

}
