package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ConditionTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.CreditHistoryTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class CreditHistoryTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("creditHistoryTabInput")
    private List<CreditHistoryTab_NoteHistory> noteHistoryList;

    private Boolean directoryOrBusinessToQuery;
}
