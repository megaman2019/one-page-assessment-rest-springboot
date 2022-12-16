package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant_Input_Response{
    private Integer id;

    private Integer appId;

    private Integer applicantId;

    private Integer matrixIdPass;

    private Integer pepSanctionMatch;

    private Integer modifiedBy;

    private Date modifiedDate;
}
