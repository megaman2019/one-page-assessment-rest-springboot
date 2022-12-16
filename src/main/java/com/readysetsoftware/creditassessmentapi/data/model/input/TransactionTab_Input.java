package com.readysetsoftware.creditassessmentapi.data.model.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ServicingTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.TransactionTab_NoteHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="tblTransactionTab_Input")
public class TransactionTab_Input implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    @NotNull(message = "refinanceConductSatisfactory should not be null.")
//    @Pattern(regexp="^[0-2]$", message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    @Range(min=0, max=2, message = "Only value 0, 1 and 2 are allowed. 0 = N/A, 1 = YES, 2 = NO")
    private Integer refinanceConductSatisfactory;

    @Size(max = 8000)
    private String notes;

    private Boolean policyException;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

//    @OneToOne
//    @JoinColumn(name="appId", insertable=false, updatable=false)
//    @JsonIgnoreProperties("transactionTab_Input")
//    private Application application;

    @OneToMany(mappedBy = "transactionTabInput")
    @JsonIgnoreProperties("transactionTabInput")
    private List<TransactionTab_NoteHistory> noteHistoryList;

}
