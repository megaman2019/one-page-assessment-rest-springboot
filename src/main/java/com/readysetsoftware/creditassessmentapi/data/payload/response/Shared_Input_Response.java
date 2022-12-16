package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Shared_Input_Response {

    private Integer id;

    private Integer appId;

    private String notes;

    private Boolean policyException;

    private Integer modifiedBy;

    private Date modifiedDate;

}
