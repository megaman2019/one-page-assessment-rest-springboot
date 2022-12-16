package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ServicingTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.SecurityTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ServicingTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ServicingTab_InputService;
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
@RequestMapping("/api/servicingTab_Input")
public class ServicingTab_InputController {
    @Autowired
    ServicingTab_InputService servicingTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<ServicingTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<ServicingTab_Input> input = servicingTabInputService.findByAppId(appId);
        ServicingTab_Input_Response response = new ServicingTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setNsr(input.get().getNsr());
            response.setNms(input.get().getNms());
            response.setNas(input.get().getNas());
            response.setDti(input.get().getDti());
            response.setLti(input.get().getLti());
            response.setSubordination(input.get().getSubordination());
            response.setLastCreditEvent(input.get().getLastCreditEvent());
            response.setNoOfCreditEvents(input.get().getNoOfCreditEvents());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<ServicingTab_Input_Response> createServicingTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ServicingTab_Input_Request servicingTabInputRequest) {

        servicingTabInputService.createServicingTabInput(appId, servicingTabInputRequest);
//        ServicingTab_Input newInput = servicingTabInputService.createServicingTabInput(appId, servicingTabInputRequest);
//        ServicingTab_Input_Response response = new ServicingTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setNsr(newInput.getNsr());
//        response.setNms(newInput.getNms());
//        response.setNas(newInput.getNas());
//        response.setDti(newInput.getDti());
//        response.setLti(newInput.getLti());
//        response.setSubordination(newInput.getSubordination());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
