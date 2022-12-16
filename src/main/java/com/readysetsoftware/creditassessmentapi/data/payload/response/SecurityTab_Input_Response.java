package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ResponsibleLendingTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.SecurityTab_NoteHistory;
import lombok.Data;

import java.util.List;
@Data
public class SecurityTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("securityTabInput")
    private List<SecurityTab_NoteHistory> noteHistoryList;
}
