package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
public class ServicingTab_Input_Request extends Shared_Input_Request{

    private Float nsr;

    private Float nms;

    private Float nas;

    private Float dti;

    private Float lti;

    private Float subordination;

    private Date lastCreditEvent;

    @Range(min= 0, max= 2, message = "Invalid value. Only allowed values are from 0 to 2")
    private Integer noOfCreditEvents;

}
