package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.LivingExpenseTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ResponsibleLendingTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class ResponsibleLendingTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("responsibleLendingTabInput")
    private List<ResponsibleLendingTab_NoteHistory> noteHistoryList;

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

}
