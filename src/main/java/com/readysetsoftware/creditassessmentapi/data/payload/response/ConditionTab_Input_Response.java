package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ConditionTab_NoteHistory;
import lombok.Data;

import java.util.List;
@Data
public class ConditionTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("conditionTabInput")
    private List<ConditionTab_NoteHistory> noteHistoryList;
}
