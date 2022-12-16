package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vBorrower_Assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAssetID")
    private Integer id;

    @Column(name = "cAssetType")
    private String assetType;

    @Column(name = "fAssetValue")
    private Float assetValue;

//    @Column(name = "cDescription")
//    private String description;

    @Column(name = "cNotes")
    private String notes;

    @Column(name = "fPercentage")
    private Float percentage;

    @ManyToOne
    @JoinColumn(name="iBorrowerID")
    @JsonIgnoreProperties("assets")
    private Borrower borrower;

}
