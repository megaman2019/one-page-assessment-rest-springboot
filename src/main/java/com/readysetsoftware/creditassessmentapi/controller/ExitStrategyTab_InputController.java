package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExceptionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExitStrategyTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ConditionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ExceptionTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ExitStrategyTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ExceptionTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.ExitStrategyTab_InputService;
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
@RequestMapping("/api/exitStrategyTab_Input")
public class ExitStrategyTab_InputController {

    @Autowired
    ExitStrategyTab_InputService exitStrategyTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<ExitStrategyTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<ExitStrategyTab_Input> input = exitStrategyTabInputService.findByAppId(appId);
        ExitStrategyTab_Input_Response response = new ExitStrategyTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setLoanTermExceedRetAge(input.get().getLoanTermExceedRetAge());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }

        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<ExitStrategyTab_Input_Response> createExitStrategyTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ExitStrategyTab_Input_Request exitStrategyTabInputRequest) {

        exitStrategyTabInputService.createExitStrategyTabInput(appId, exitStrategyTabInputRequest);
//        ExitStrategyTab_Input newInput = exitStrategyTabInputService.createExitStrategyTabInput(appId, exitStrategyTabInputRequest);
//        ExitStrategyTab_Input_Response response = new ExitStrategyTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setLoanTermExceedRetAge(newInput.getLoanTermExceedRetAge());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
