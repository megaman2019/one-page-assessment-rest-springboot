package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.noteHistory.ApplicantTab_NoteHistory;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ApplicantTab_InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/applicantTab_Input")
public class ApplicantTab_InputController {
    @Autowired
    ApplicantTab_InputService applicantTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<ApplicantTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<ApplicantTab_Input> input = applicantTabInputService.findByAppId(appId);
        ApplicantTab_Input_Response response = new ApplicantTab_Input_Response();
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
    public ResponseEntity<ApplicantTab_Input_Response> createApplicantTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ApplicantTab_Input_Request applicantTabInputRequest) {

        applicantTabInputService.createApplicantTabInput(appId, applicantTabInputRequest);
//        ApplicantTab_Input newInput = applicantTabInputService.createApplicantTabInput(appId, applicantTabInputRequest);
//        ApplicantTab_Input_Response applicantTabInputResponse = new ApplicantTab_Input_Response();
//
//        applicantTabInputResponse.setId(newInput.getId());
//        applicantTabInputResponse.setAppId(newInput.getAppId());
//        applicantTabInputResponse.setNotes(newInput.getNotes());
//        applicantTabInputResponse.setPolicyException(newInput.getPolicyException());
//        applicantTabInputResponse.setModifiedBy(newInput.getModifiedBy());
//        applicantTabInputResponse.setModifiedDate(newInput.getModifiedDate());

        return getData(appId);

    }

//    private ApplicantTab_Input_Response findByAppID(Integer appId){
//        Optional<ApplicantTab_Input> input = applicantTabInputService.findByAppId(appId);
//        ApplicantTab_Input_Response response = new ApplicantTab_Input_Response();
//        if (input.isPresent()) {
//            response.setId(input.get().getId());
//            response.setAppId(input.get().getAppId());
//            response.setNotes(input.get().getNotes());
//            response.setPolicyException(input.get().getPolicyException());
//            response.setModifiedBy(input.get().getModifiedBy());
//            response.setModifiedDate(input.get().getModifiedDate());
//            response.setNoteHistoryList(input.get().getNoteHistoryList());
//        }
//        return response;
//    }

}
