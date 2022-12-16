package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResultRequest {

    @Column(name = "iSN")
    private Integer id;

    @NotNull(message = "Rule should not be null.")
    @NotEmpty(message = "Rule should not be empty.")
    @Column(name = "cRule")
    private String rule;

    @Column(name = "cAppValue")
    private String appValue;

    @Column(name = "cRuleValue")
    private String ruleValue;

    @NotNull(message = "Result should not be null.")
    @NotEmpty(message = "Result should not be empty.")
    @Column(name = "cResult")
    private String result;

    @NotNull(message = "Type should not be null.")
    @NotEmpty(message = "Type should not be empty.")
    @Column(name = "cType")
    private String type;
}
