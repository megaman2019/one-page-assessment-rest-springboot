package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.Applicant_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.Applicant_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.Applicant_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.Applicant_InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/applicantTab_Input/applicant_Input")
public class Applicant_InputController {
    @Autowired
    Applicant_InputService applicantInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<List<Applicant_Input>> getData(@PathVariable("appId") Integer appId) {
        List<Applicant_Input> applicantInputs = applicantInputService.findByAppId(appId);
        return new ResponseEntity<> (applicantInputs, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<Applicant_Input_Response> createApplicantInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody Applicant_Input_Request applicantInputRequest) {

        Applicant_Input newInput = applicantInputService.createApplicantInput(appId, applicantInputRequest);
        Applicant_Input_Response applicantInputResponse = new Applicant_Input_Response();

        applicantInputResponse.setId(newInput.getId());
        applicantInputResponse.setAppId(newInput.getAppId());
        applicantInputResponse.setApplicantId(newInput.getApplicantId());
        applicantInputResponse.setMatrixIdPass(newInput.getMatrixIdPass());
        applicantInputResponse.setPepSanctionMatch(newInput.getPepSanctionMatch());
        applicantInputResponse.setModifiedBy(newInput.getModifiedBy());
        applicantInputResponse.setModifiedDate(newInput.getModifiedDate());

        return new ResponseEntity<>(applicantInputResponse, HttpStatus.OK);

    }

}
