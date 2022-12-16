package com.readysetsoftware.creditassessmentapi.data.model.conditions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="vApplicationConditions")
public class ApplicationCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppConditionID")
    private Integer id;

    @Column(name = "cConditionDesc")
    private String conditionDesc;

    @Column(name = "cStatus")
    private String status;

    @Column(name = "dReceived")
    private Date received;

    @Column(name = "dCompleted")
    private Date completed;

    @Column(name = "iOrder")
    private Integer order;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("applicationConditions")
    private Application application;

}
