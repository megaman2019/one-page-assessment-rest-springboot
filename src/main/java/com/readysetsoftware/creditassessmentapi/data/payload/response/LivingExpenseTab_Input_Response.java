package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.IncomeTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.LivingExpenseTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class LivingExpenseTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("livingExpenseTabInput")
    private List<LivingExpenseTab_NoteHistory> noteHistoryList;

    private Boolean dleLesserThanHem;

    private Float hem;

}
