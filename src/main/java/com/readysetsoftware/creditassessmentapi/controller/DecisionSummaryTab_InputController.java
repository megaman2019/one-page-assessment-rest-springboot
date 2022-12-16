package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.DecisionSummaryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.CreditHistoryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.DecisionSummaryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ConditionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.CreditHistoryTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.DecisionSummaryTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.CreditHistoryTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.DecisionSummaryTab_InputService;
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
@RequestMapping("/api/decisionSummaryTab_Input")
public class DecisionSummaryTab_InputController {

    @Autowired
    DecisionSummaryTab_InputService decisionSummaryTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<DecisionSummaryTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<DecisionSummaryTab_Input> input = decisionSummaryTabInputService.findByAppId(appId);
        DecisionSummaryTab_Input_Response response = new DecisionSummaryTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setDeclineCompleteDate(input.get().getDeclineCompleteDate());
            response.setConditionalCompleteDate(input.get().getConditionalCompleteDate());
            response.setMirCompleteDate(input.get().getMirCompleteDate());
            response.setFormalCompleteDate(input.get().getFormalCompleteDate());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }

        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<DecisionSummaryTab_Input_Response> createDecisionSummaryTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody DecisionSummaryTab_Input_Request decisionSummaryTabInputRequest) {

        decisionSummaryTabInputService.createDecisionSummaryTabInput(appId, decisionSummaryTabInputRequest);
//        DecisionSummaryTab_Input newInput = decisionSummaryTabInputService.createDecisionSummaryTabInput(appId, decisionSummaryTabInputRequest);
//        DecisionSummaryTab_Input_Response response = new DecisionSummaryTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setDeclineCompleteDate(newInput.getDeclineCompleteDate());
//        response.setConditionalCompleteDate(newInput.getConditionalCompleteDate());
//        response.setMirCompleteDate(newInput.getMirCompleteDate());
//        response.setFormalCompleteDate(newInput.getFormalCompleteDate());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
