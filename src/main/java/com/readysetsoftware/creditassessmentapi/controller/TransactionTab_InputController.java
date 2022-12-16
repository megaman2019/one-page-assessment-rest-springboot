package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.TransactionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.SecurityTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.TransactionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ApplicantTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.TransactionTab_InputService;
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
@RequestMapping("/api/transactionTab_Input")
public class TransactionTab_InputController {
    @Autowired
    TransactionTab_InputService transactionTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<TransactionTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<TransactionTab_Input> input = transactionTabInputService.findByAppId(appId);
        TransactionTab_Input_Response response = new TransactionTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setRefinanceConductSatisfactory(input.get().getRefinanceConductSatisfactory());
            response.setAppId(input.get().getAppId());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<TransactionTab_Input_Response> createTransactionTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody TransactionTab_Input_Request transactionTabInputRequest) {

        transactionTabInputService.createTransactionTabInput(appId, transactionTabInputRequest);
//        TransactionTab_Input newInput = transactionTabInputService.createTransactionTabInput(appId, transactionTabInputRequest);
//        TransactionTab_Input_Response response = new TransactionTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setRefinanceConductSatisfactory(newInput.getRefinanceConductSatisfactory());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
