package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

@Data
public class ResponsibleLendingTab_Input_Request extends Shared_Input_Request{

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
