package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.IncomeTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.LivingExpenseTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.IncomeTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.LivingExpenseTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.IncomeTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.LivingExpenseTab_InputService;
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
@RequestMapping("/api/livingExpenseTab_Input")
public class LivingExpenseTab_InputController {

    @Autowired
    LivingExpenseTab_InputService livingExpenseTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<LivingExpenseTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<LivingExpenseTab_Input> input = livingExpenseTabInputService.findByAppId(appId);
        LivingExpenseTab_Input_Response response = new LivingExpenseTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setHem(input.get().getHem());
            response.setDleLesserThanHem(input.get().getDleLesserThanHem());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<LivingExpenseTab_Input_Response> createLivingExpenseTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody LivingExpenseTab_Input_Request livingExpenseTabInputRequest) {

        livingExpenseTabInputService.createLivingExpenseTabInput(appId, livingExpenseTabInputRequest);
//        LivingExpenseTab_Input newInput = livingExpenseTabInputService.createLivingExpenseTabInput(appId, livingExpenseTabInputRequest);
//        LivingExpenseTab_Input_Response response = new LivingExpenseTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setHem(newInput.getAppId());
//        response.setDleLesserThanHem(newInput.getDleLesserThanHem());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);

        return getData(appId);

    }
}
