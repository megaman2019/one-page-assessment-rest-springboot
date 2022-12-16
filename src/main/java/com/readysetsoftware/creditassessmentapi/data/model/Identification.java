package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vBorrower_Identifications")
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iBorrowerIdentificationID")
    private Integer id;

    @Column(name = "cDocumentNumber")
    private String documentNumber;

    @Column(name = "cDocumentType")
    private String documentType;

    @ManyToOne
    @JoinColumn(name="iBorrowerID")
    @JsonIgnoreProperties("identifications")
    private Borrower borrower;


}
