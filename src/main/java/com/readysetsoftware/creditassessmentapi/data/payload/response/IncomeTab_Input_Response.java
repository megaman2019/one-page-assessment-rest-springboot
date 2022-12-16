package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ExitStrategyTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.IncomeTab_NoteHistory;
import lombok.Data;

import java.util.List;
@Data
public class IncomeTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("incomeTabInput")
    private List<IncomeTab_NoteHistory> noteHistoryList;
}
