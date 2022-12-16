package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TransactionTab_Input_Request extends Shared_Input_Request{

    @NotNull(message = "refinanceConductSatisfactory should not be null.")
//    @Pattern(regexp="^[0-2]$", message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer refinanceConductSatisfactory;

}
