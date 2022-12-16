package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

@Data
public class ExitStrategyTab_Input_Request extends Shared_Input_Request{

    private Boolean loanTermExceedRetAge;

}
