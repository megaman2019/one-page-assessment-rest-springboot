package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionTab_Input_Response{

    private Integer id;

    private Integer appId;

    private String tabName;

    private String input;

    private Integer modifiedBy;

    private Date modifiedDate;

}
