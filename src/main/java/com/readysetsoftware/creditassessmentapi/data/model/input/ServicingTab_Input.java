package com.readysetsoftware.creditassessmentapi.data.model.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.SecurityTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ServicingTab_NoteHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

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
@Table(name ="tblServicingTab_Input")
public class ServicingTab_Input implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "AppId should not be null.")
    private Integer appId;

    private Float nsr;

    private Float nms;

    private Float nas;

    private Float dti;

    private Float lti;

    private Float subordination;

    private Date lastCreditEvent;

    @Range(min= 0, max= 2, message = "Invalid value. Only allowed values are from 0 to 2")
    private Integer noOfCreditEvents;

    @Size(max = 8000)
    private String notes;

    private Boolean policyException;

    @NotNull(message = "ModifiedBy should not be null.")
    private Integer modifiedBy;

    @NotNull(message = "ModifiedDate should not be null.")
    private Date modifiedDate;

    @OneToMany(mappedBy = "servicingTabInput")
    @JsonIgnoreProperties("servicingTabInput")
    private List<ServicingTab_NoteHistory> noteHistoryList;

}
