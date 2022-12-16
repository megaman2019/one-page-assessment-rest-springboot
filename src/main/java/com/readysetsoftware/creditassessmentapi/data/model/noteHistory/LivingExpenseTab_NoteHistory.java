package com.readysetsoftware.creditassessmentapi.data.model.noteHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
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
@Table(name ="vLivingExpenseTab_NoteHistory")
public class LivingExpenseTab_NoteHistory extends Shared_NoteHistory {

    @ManyToOne
    @JoinColumn(
        name = "iAppID",
        referencedColumnName = "appId"
    )
    @JsonIgnoreProperties("noteHistoryList")
    private LivingExpenseTab_Input livingExpenseTabInput;

}
