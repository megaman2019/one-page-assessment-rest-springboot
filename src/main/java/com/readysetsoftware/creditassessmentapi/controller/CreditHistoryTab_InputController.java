package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.CreditHistoryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ConditionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.CreditHistoryTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ApplicantTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.CreditHistoryTab_InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/creditHistoryTab_Input")
public class CreditHistoryTab_InputController {
    @Autowired
    CreditHistoryTab_InputService creditHistoryTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<CreditHistoryTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<CreditHistoryTab_Input> input = creditHistoryTabInputService.findByAppId(appId);
        CreditHistoryTab_Input_Response response = new CreditHistoryTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setDirectoryOrBusinessToQuery(input.get().getDirectoryOrBusinessToQuery());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }

        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<CreditHistoryTab_Input_Response> createApplicantTabInput(@PathVariable("appId") Integer appId, @Valid @RequestBody CreditHistoryTab_Input_Request creditHistoryTabInputRequest) {

        creditHistoryTabInputService.createCreditHistoryTabInput(appId, creditHistoryTabInputRequest);
//        CreditHistoryTab_Input newInput = creditHistoryTabInputService.createCreditHistoryTabInput(appId, creditHistoryTabInputRequest);
//        CreditHistoryTab_Input_Response response = new CreditHistoryTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setDirectoryOrBusinessToQuery(newInput.getDirectoryOrBusinessToQuery());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
