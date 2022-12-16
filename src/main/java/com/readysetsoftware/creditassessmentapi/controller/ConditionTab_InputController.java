package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ConditionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.AssetAndLiabilityTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ConditionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ConditionTab_InputService;
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
@RequestMapping("/api/conditionTab_Input")
public class ConditionTab_InputController {
    @Autowired
    ConditionTab_InputService conditionTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<ConditionTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<ConditionTab_Input> input = conditionTabInputService.findByAppId(appId);
        ConditionTab_Input_Response response = new ConditionTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
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
    public ResponseEntity<ConditionTab_Input_Response> createConditionTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ConditionTab_Input_Request conditionTabInputRequest) {

        conditionTabInputService.createConditionTabInput(appId, conditionTabInputRequest);
//        ConditionTab_Input newInput = conditionTabInputService.createConditionTabInput(appId, conditionTabInputRequest);
//        ConditionTab_Input_Response response = new ConditionTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
