package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Application> getApplicationById(@PathVariable("id") Integer id){
        Application application = applicationService.getApplicationById(id);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }
}
