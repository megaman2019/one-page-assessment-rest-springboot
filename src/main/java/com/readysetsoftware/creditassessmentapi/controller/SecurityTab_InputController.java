package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.SecurityTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.LivingExpenseTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.SecurityTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ApplicantTab_InputService;
import com.readysetsoftware.creditassessmentapi.service.SecurityTab_InputService;
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
@RequestMapping("/api/securityTab_Input")
public class SecurityTab_InputController {
    @Autowired
    SecurityTab_InputService securityTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<SecurityTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<SecurityTab_Input> input = securityTabInputService.findByAppId(appId);
        SecurityTab_Input_Response response = new SecurityTab_Input_Response();
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
    public ResponseEntity<SecurityTab_Input_Response> createSecurityTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody SecurityTab_Input_Request securityTabInputRequest) {

        securityTabInputService.createSecurityTabInput(appId, securityTabInputRequest);
//        SecurityTab_Input newInput = securityTabInputService.createSecurityTabInput(appId, securityTabInputRequest);
//        SecurityTab_Input_Response response = new SecurityTab_Input_Response();
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
