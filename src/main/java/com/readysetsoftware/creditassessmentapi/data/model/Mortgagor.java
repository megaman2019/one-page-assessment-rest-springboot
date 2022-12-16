package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vMortgagors")
public class Mortgagor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iMortgagorID")
    private Integer id;

    @Column(name = "iBorrowerID")
    private Integer borrowerId;

    @Column(name = "cFirstNameOnly")
    private String firstName;

    @Column(name = "cMiddleName")
    private String middleName;

    @Column(name = "cSurname")
    private String surname;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("mortgagors")
    private Application application;

}
