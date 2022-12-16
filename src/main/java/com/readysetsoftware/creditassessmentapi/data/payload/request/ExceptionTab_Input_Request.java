package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ExceptionTab_Input_Request {

    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    @Size(max = 100)
    private String tabName;

    @Size(max = 100)
    private String input;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;


}
