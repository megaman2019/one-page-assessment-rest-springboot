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
@Table(name ="vProduct_Fees")
public class ProductFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iFeeID")
    private Integer id;

    @Column(name = "cFeeName")
    private String feeName;

    @Column(name = "fAmount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name="iAppProductID")
    @JsonIgnoreProperties("productFees")
    private Product product;

}
