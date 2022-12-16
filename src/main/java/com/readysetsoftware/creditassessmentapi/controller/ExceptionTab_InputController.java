package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExceptionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ExceptionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ExceptionTab_InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/exceptionTab_Input")
public class ExceptionTab_InputController {

    @Autowired
    ExceptionTab_InputService exceptionTabInputService;

    @PostMapping("/create/{appId}")
    public ResponseEntity<ExceptionTab_Input_Response> createExceptionTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ExceptionTab_Input_Request exceptionTabInputRequest) {

        ExceptionTab_Input newInput = exceptionTabInputService.createExceptionTabInput(appId, exceptionTabInputRequest);
        ExceptionTab_Input_Response exceptionTabInputResponse = new ExceptionTab_Input_Response();

        exceptionTabInputResponse.setId(newInput.getId());
        exceptionTabInputResponse.setAppId(newInput.getAppId());
        exceptionTabInputResponse.setTabName(newInput.getTabName());
        exceptionTabInputResponse.setInput(newInput.getInput());
        exceptionTabInputResponse.setModifiedBy(newInput.getModifiedBy());
        exceptionTabInputResponse.setModifiedDate(newInput.getModifiedDate());

        return new ResponseEntity<>(exceptionTabInputResponse, HttpStatus.OK);

    }
}
