package com.readysetsoftware.creditassessmentapi.data.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {

    private Integer id;

    @NotNull(message = "appId should not be null.")
    private Integer appId;

    @NotNull(message = "noteType should not be null.")
    @NotEmpty(message = "noteType should not be empty.")
    private String noteType;

    @NotNull(message = "noteDesc should not be null.")
    @NotEmpty(message = "noteDesc should not be empty.")
    private String noteDesc;

    @NotNull(message = "userId should not be null.")
    private Integer userId;

}
