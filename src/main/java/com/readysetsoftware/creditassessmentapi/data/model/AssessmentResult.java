package com.readysetsoftware.creditassessmentapi.data.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vAppCreditDecision")
public class AssessmentResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iSN")
    private Integer id;

    @NotNull(message = "Rule should not be null.")
    @NotEmpty(message = "Rule should not be empty.")
    @Column(name = "cRule")
    private String rule;

    @Column(name = "cAppValue")
    private String appValue;

    @Column(name = "cRuleValue")
    private String ruleValue;

    @NotNull(message = "Result should not be null.")
    @NotEmpty(message = "Result should not be empty.")
    @Column(name = "cResult")
    private String result;

    @NotNull(message = "Type should not be null.")
    @NotEmpty(message = "Type should not be empty.")
    @Column(name = "cType")
    private String type;

    @ManyToOne
    @JoinColumn(name="iBorrowerID")
    @JsonIgnoreProperties("assessmentResults")
    private Borrower borrower;

}
