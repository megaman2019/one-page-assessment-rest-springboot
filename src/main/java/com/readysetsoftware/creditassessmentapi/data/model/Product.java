package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vProducts")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppProductID")
    private Integer id;

    @Column(name = "cAPProductName")
    private String productName;

    @Column(name = "fAPBorrowerRate")
    private Float borrowerRate;

    @Column(name = "fAPAmount")
    private Float amount;

    @Column(name = "fAPRepayments")
    private Float repayments;

    @Column(name = "dAPMaturity")
    private Date maturity;

    @Column(name = "cAPProductType")
    private String productType;

    @Column(name = "iAPTermYear")
    private Integer termYear;

    @Column(name = "iAPTermMonth")
    private Integer termMonth;

    @Column(name = "cAPFinancier")
    private String financier;

    @Column(name = "cPortionCode")
    private String portionCode;

//    @Column(name = "cFeeName")
//    private String feeName;
//
//    @Column(name = "fFeeAmount")
//    private Float feeAmount;

//    @Column(name = "varLoanPurposeDesc")
//    private String loanPurposeDesc;
//
//    @Column(name = "fLoanPurposeAmount")
//    private Float loanPurposeAmount;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("products")
    private Application application;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<ProductFee> productFees;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<ProductLoanPurpose> productLoanPurposes;

}
