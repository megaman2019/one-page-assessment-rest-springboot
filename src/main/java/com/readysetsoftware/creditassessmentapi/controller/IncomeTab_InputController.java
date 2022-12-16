package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.IncomeTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ExitStrategyTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.IncomeTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.IncomeTab_InputService;
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
@RequestMapping("/api/incomeTab_Input")
public class IncomeTab_InputController {
    @Autowired
    IncomeTab_InputService incomeTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<IncomeTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<IncomeTab_Input> input = incomeTabInputService.findByAppId(appId);
        IncomeTab_Input_Response response = new IncomeTab_Input_Response();
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
    public ResponseEntity<IncomeTab_Input_Response> createIncomeTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody IncomeTab_Input_Request incomeTabInputRequest) {

        incomeTabInputService.createIncomeTabInput(appId, incomeTabInputRequest);
//        IncomeTab_Input newInput = incomeTabInputService.createIncomeTabInput(appId, incomeTabInputRequest);
//        IncomeTab_Input_Response response = new IncomeTab_Input_Response();
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
