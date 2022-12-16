package com.readysetsoftware.creditassessmentapi.data.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="tblApplicant_Input")
public class Applicant_Input implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    @NotNull(message = "ApplicantId should not be null.")
    private Integer applicantId;

    @NotNull(message = "matrixIdPass should not be null.")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer matrixIdPass;

    @NotNull(message = "pepSanctionMatch should not be null.")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer pepSanctionMatch;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

}
