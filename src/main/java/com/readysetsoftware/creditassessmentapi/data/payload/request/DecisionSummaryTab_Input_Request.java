package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class DecisionSummaryTab_Input_Request extends Shared_Input_Request{

    private Date declineCompleteDate;

    private Date mirCompleteDate;

    private Date conditionalCompleteDate;

    private Date formalCompleteDate;

}
