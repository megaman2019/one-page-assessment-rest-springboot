package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ExceptionResponse;
import com.readysetsoftware.creditassessmentapi.data.repository.ExceptionRepository;
import com.readysetsoftware.creditassessmentapi.service.ApplicantTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/exception")
public class ExceptionController {

    @Autowired
    ExceptionService exceptionService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<List<Exception>> getData(@PathVariable("appId") Integer appId) {
        List<Exception> exceptions = exceptionService.findByAppId(appId);

        return new ResponseEntity<> (exceptions, HttpStatus.OK);
    }

}
