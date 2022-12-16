package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Integer id;

    private Integer appId;

    private String tabName;

    private Boolean policyException;

}
