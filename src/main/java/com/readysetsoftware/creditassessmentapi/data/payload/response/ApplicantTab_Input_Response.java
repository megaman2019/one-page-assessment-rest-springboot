package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ApplicantTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class ApplicantTab_Input_Response extends Shared_Input_Response{
    @JsonIgnoreProperties("applicantTabInput")
    private List<ApplicantTab_NoteHistory> noteHistoryList;
}
