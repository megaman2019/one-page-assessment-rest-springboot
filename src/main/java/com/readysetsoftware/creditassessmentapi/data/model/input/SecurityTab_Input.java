package com.readysetsoftware.creditassessmentapi.data.model.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ResponsibleLendingTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.SecurityTab_NoteHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="tblSecurityTab_Input")
public class SecurityTab_Input implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    @Size(max = 8000)
    private String notes;

    private Boolean policyException;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

//    @OneToOne
//    @JoinColumn(name="appId", insertable=false, updatable=false)
//    @JsonIgnoreProperties("securityTab_Input")
//    private Application application;

    @OneToMany(mappedBy = "securityTabInput")
    @JsonIgnoreProperties("securityTabInput")
    private List<SecurityTab_NoteHistory> noteHistoryList;

}
