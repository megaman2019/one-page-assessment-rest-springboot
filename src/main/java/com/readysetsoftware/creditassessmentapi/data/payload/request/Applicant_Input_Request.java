package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class Applicant_Input_Request{

    @Id
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    @NotNull(message = "ApplicantId should not be null.")
    private Integer applicantId;

    @NotNull(message = "matrixIdPass should not be null.")
//    @Pattern(regexp="^[0-2]$", message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer matrixIdPass;

    @NotNull(message = "pepSanctionMatch should not be null.")
//    @Pattern(regexp="^[0-2]$", message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer pepSanctionMatch;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

}
