package com.readysetsoftware.creditassessmentapi.data.model;

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
@Table(name ="vCreditHistory")
public class CreditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppCreditSummaryID")
    private Integer id;

    @Column(name = "iAppID")
    private Integer appId;

    @Column(name = "iCustID")
    private Integer custId;

    @Column(name = "iBorrowerID")
    private Integer applicantId;

    @Column(name = "iDefaults")
    private Integer defaults;

    @Column(name = "iWritsAndSummons")
    private Integer writsAndSummons;

    @Column(name = "iBankruptcies")
    private Integer bankruptcies;

    @Column(name = "iJudgements")
    private Integer judgements;

    @Column(name = "iDirectorships")
    private Integer directorships;

    @Column(name = "iEnquiriesLastYear")
    private Integer enquiriesLastYear;

    @Column(name = "iTotalEnquiries")
    private Integer totalEnquiries;

    @Column(name = "cVedaScore")
    private Integer score;

    @Column(name = "cFirstName")
    private String firstName;

    @Column(name = "cMiddleName")
    private String middleName;

    @Column(name = "cSurname")
    private String surname;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "iCustID", referencedColumnName = "iCustID", insertable=false, updatable=false)
//    @JsonIgnoreProperties("creditHistories")
//    private Borrower borrower;

}
