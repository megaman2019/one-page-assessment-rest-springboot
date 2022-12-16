package com.readysetsoftware.creditassessmentapi.data.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.TransactionTab_NoteHistory;
import lombok.Data;

import java.util.List;

@Data
public class TransactionTab_Input_Response extends Shared_Input_Response{

    @JsonIgnoreProperties("transactionTabInput")
    private List<TransactionTab_NoteHistory> noteHistoryList;

    private Integer refinanceConductSatisfactory;
}
