package com.readysetsoftware.creditassessmentapi.data.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="tbl_CA_Note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "NoteType should not be null.")
    @NotEmpty(message = "NoteType should not be empty.")
    @Column(name = "cNoteType")
    private String noteType;

    @NotNull(message = "NoteDesc should not be null.")
    @NotEmpty(message = "NoteDesc should not be empty.")
    @Column(name = "cNoteDesc")
    private String noteDesc;

    @NotNull(message = "AppId should not be null.")
    @Column(name = "iAppID")
    private Integer appId;

    @NotNull(message = "UserId should not be null.")
    @Column(name = "iUserId")
    private Integer userId;

    @ManyToOne()
    @JoinColumn(name="iAppID", insertable=false, updatable=false)
    @JsonIgnore
    private Application application;


}
