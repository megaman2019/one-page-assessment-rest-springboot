package com.readysetsoftware.creditassessmentapi.data.model.noteHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Shared_NoteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iAppHistoryID")
    private Integer id;

    @Column(name = "cNotes")
    private String notes;

    @Column(name = "dDateStamp")
    private Date dateStamp;

    @Column(name = "cFullName")
    private String creator;
}
