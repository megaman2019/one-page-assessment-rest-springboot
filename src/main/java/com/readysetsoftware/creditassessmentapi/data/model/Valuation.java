package com.readysetsoftware.creditassessmentapi.data.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vSecurity_Valuation")
public class Valuation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "cZoning")
    private String zoning;

    @Column(name = "iLandArea")
    private Integer landArea;

    @Column(name = "cUnitOfMeasurement")
    private String unitOfMeasurement;

    @Column(name = "fLivingAreaInSquareMetres")
    private Float livingAreaInSquareMetres;

    @Column(name = "iNoOfUnits")
    private Integer noOfUnits;

    @Column(name = "iLandRating")
    private Integer landRating;

    @Column(name = "iNeighbourhoodRating")
    private Integer neighbourhoodRating;

    @Column(name = "iEnvironmentalRating")
    private Integer environmentalRating;

    @Column(name = "iImprovementsRating")
    private Integer improvementsRating;

    @Column(name = "iReducedValueNextYearsRating")
    private Integer reducedValueNextYearsRating;

    @Column(name = "iMarketVolatilityRating")
    private Integer marketVolatilityRating;

    @Column(name = "iMarketSegmentConditionRating")
    private Integer marketSegmentConditionRating;

    @Column(name = "dValuationDate")
    private Date valuationDate;

    @Column(name = "iLocalEconomyImpactRating")
    private Integer localEconomyImpactRating;
    @ManyToOne
    @JoinColumn(name="iSecurityID")
    @JsonIgnoreProperties("valuations")
    private Security security;

}
