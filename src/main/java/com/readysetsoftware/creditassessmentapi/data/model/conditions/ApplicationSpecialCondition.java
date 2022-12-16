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
@Table(name ="vApplicationSpecialConditions")
public class ApplicationSpecialCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iSpecialConditionID")
    private Integer id;

    @Column(name = "cSpecialConditionDesc")
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
    @JsonIgnoreProperties("applicationSpecialConditions")
    private Application application;

}
