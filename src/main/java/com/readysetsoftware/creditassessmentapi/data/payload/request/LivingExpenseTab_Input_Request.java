package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LivingExpenseTab_Input_Request extends Shared_Input_Request{

    private Boolean dleLesserThanHem;

    private Float hem;

}
