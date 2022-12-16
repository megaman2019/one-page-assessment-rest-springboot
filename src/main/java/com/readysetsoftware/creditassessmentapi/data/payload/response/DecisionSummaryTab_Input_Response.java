package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.CreditHistoryTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.DecisionSummaryTab_NoteHistory;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DecisionSummaryTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("decisionSummaryTabInput")
    private List<DecisionSummaryTab_NoteHistory> noteHistoryList;

    private Date declineCompleteDate;

    private Date mirCompleteDate;

    private Date conditionalCompleteDate;

    private Date formalCompleteDate;

}
