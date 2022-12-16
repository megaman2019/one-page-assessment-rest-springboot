package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.DecisionSummaryTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ExitStrategyTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class ExitStrategyTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("exitStrategyTabInput")
    private List<ExitStrategyTab_NoteHistory> noteHistoryList;

    private Boolean loanTermExceedRetAge;

}
