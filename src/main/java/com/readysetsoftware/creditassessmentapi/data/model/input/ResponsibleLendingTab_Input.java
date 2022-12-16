package com.readysetsoftware.creditassessmentapi.data.model.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.LivingExpenseTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ResponsibleLendingTab_NoteHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="tblResponsibleLendingTab_Input")
public class ResponsibleLendingTab_Input implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    private Boolean costAndRiskOngoingFee;

    private Boolean costAndRiskBreakCosts;

    private Boolean costAndRiskLMI;

    private Boolean costAndRiskRefinanceCosts;

    private Boolean loanRepayTypePrincipalAndInterest;

    private Boolean loanRepayTypeInterestOnly;

    private Boolean loanRepayTypeFixed;

    private Boolean loanRepayTypeVariable;

    private Boolean objectivePurchase;

    private Boolean objectiveRefinance;

    private Boolean objectiveDebtConsolidation;

    private Boolean objectiveLowInterestRate;

    private Boolean objectiveFinancialGoals;

    private Boolean objectiveOther;

    private Boolean applicationNotSuitable;

    private Boolean applicationMeetRequirements;

    @Size(max = 8000)
    private String notes;

    private Boolean policyException;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

//    @OneToOne
//    @JoinColumn(name="appId", insertable=false, updatable=false)
//    @JsonIgnoreProperties("responsibleLendingTab_Input")
//    private Application application;

    @OneToMany(mappedBy = "responsibleLendingTabInput")
    @JsonIgnoreProperties("responsibleLendingTabInput")
    private List<ResponsibleLendingTab_NoteHistory> noteHistoryList;

}
