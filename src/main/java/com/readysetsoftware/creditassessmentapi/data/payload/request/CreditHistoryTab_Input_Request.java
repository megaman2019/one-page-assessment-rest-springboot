package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

@Data
public class CreditHistoryTab_Input_Request extends Shared_Input_Request{
    private Boolean directoryOrBusinessToQuery;

}
