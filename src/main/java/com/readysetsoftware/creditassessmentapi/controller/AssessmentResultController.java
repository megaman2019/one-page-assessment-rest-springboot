package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.AssessmentResult;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssessmentResultRequest;
import com.readysetsoftware.creditassessmentapi.service.AssessmentResultService;
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
@RequestMapping("/api/assessment")
public class AssessmentResultController{

    @Autowired
    AssessmentResultService assessmentResultService;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<AssessmentResult>> findAllAssessmentResult() {
        List<AssessmentResult> assessmentResults = assessmentResultService.findAllAssessmentResult();
        return new ResponseEntity<>(assessmentResults, HttpStatus.OK);
    }

    @GetMapping(value = "/find/{id}", produces = "application/json")
    public ResponseEntity<AssessmentResult> getAllAssessmentResult(@PathVariable("id") Integer id) {
        AssessmentResult assessmentResult = assessmentResultService.getSingleAssessmentResult(id);
        return new ResponseEntity<>(assessmentResult, HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<AssessmentResult> addAssessmentResult(@Valid @RequestBody AssessmentResultRequest newAssessment){
            AssessmentResult assessmentResult = assessmentResultService.createAssessmentResult(newAssessment);
            return new ResponseEntity<>(assessmentResult, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AssessmentResult> updateAssessmentResult(@PathVariable("id") Integer id, @Valid @RequestBody AssessmentResultRequest assessmentResultRequest){
        Optional<AssessmentResult> assessmentResult = assessmentResultService.updateAssessmentResult(id, assessmentResultRequest);
        return new ResponseEntity<AssessmentResult>(assessmentResult.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAssessmentResult(@PathVariable("id") Integer id) {
        assessmentResultService.deleteAssessmentResult(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
