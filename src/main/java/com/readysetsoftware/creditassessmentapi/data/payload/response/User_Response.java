package com.readysetsoftware.creditassessmentapi.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User_Response {

    private Integer id;

    private String username;

    private String role;

}
