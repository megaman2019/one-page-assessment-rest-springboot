package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import lombok.Data;

import java.util.List;
@Data
public class AssetAndLiabilityTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("assetAndLiabilityTabInput")
    private List<AssetAndLiabilityTab_NoteHistory> noteHistoryList;
}
