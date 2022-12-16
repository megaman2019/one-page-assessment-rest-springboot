package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vSecurities")
public class Security {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iSecurityID")
    private Integer id;

    @Column(name = "cPropertyType")
    private String propertyType;

    @Column(name = "fPurchasePrice")
    private Float purchasePrice;

    @Column(name = "fEstimatedValue")
    private Float estimatedValue;

    @Column(name = "fValuationValue")
    private Float valuationValue;

    @Column(name = "fInsuranceReplacementAmount")
    private Float insuranceReplacementAmount;

    @Column(name = "fLegalFees")
    private Float legalFees;

    @Column(name = "cCapitalized")
    private String capitalized;

    @Column(name = "cSecurityAddress")
    private String securityAddress;

    @Column(name = "cCategory")
    private String category;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("securities")
    private Application application;

    @OneToMany(mappedBy = "security")
    @JsonIgnoreProperties("security")
    private List<Valuation> valuations;



}