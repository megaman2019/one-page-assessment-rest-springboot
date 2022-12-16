package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vProduct_LoanPurpose")
public class ProductLoanPurpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "varLoanPurposeDesc")
    private String loanPurposeDesc;

    @Column(name = "fLoanPurposeAmount")
    private float loanPurposeAmount;

//    @Column(name = "cPortionCode")
//    private String portionCode;
//
//    @Column(name = "cAPProductName")
//    private String productName;
//
//    @Column(name = "fAPAmount")
//    private float productAmount;
//
//    @Column(name = "cFinancier")
//    private String financier;

    @ManyToOne
    @JoinColumn(name="iAppProductID")
    @JsonIgnoreProperties("productLoanPurposes")
    private Product product;
}
