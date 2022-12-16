package com.readysetsoftware.creditassessmentapi.data.model.noteHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
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
@Table(name ="vSecurityTab_NoteHistory")
public class SecurityTab_NoteHistory extends Shared_NoteHistory {
    @ManyToOne
    @JoinColumn(
        name = "iAppID",
        referencedColumnName = "appId"
    )
    @JsonIgnoreProperties("noteHistoryList")
    private SecurityTab_Input securityTabInput;

}
