package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.AssetAndLiabilityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.SecurityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ServicingTab_NoteHistory;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ServicingTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("servicingTabInput")
    private List<ServicingTab_NoteHistory> noteHistoryList;

    private Float nsr;

    private Float nms;

    private Float nas;

    private Float dti;

    private Float lti;

    private Float subordination;

    private Date lastCreditEvent;

    private Integer noOfCreditEvents;

}
