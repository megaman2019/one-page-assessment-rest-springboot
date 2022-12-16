package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteResponse {
    private Integer id;
    private Integer appId;
    private String noteType;
    private String noteDesc;
    private Integer userId;
}
