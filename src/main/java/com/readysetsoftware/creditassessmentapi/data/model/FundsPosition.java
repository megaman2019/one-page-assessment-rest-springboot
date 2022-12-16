package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vFundsPosition")
public class FundsPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "iFundsPositionId")
    private Integer fundsPositionId;

    @Column(name = "cFundsPosition")
    private String fundsPosition;

    @Column(name = "fAmount")
    private Float amount;

    @Column(name = "cVerificationType")
    private String verificationType;

    @Column(name = "fMonthlyRepayment")
    private Float monthlyRepayment;

    @Column(name = "cInstitution")
    private String institution;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("fundsPositions")
    private Application application;

}