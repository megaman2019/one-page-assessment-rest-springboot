package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssetAndLiabilityTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ApplicantTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.AssetAndLiabilityTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.AssetAndLiabilityTab_InputService;
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
@RequestMapping("/api/assetAndLiabilityTab_Input")
public class AssetAndLiabilityTab_InputController {
    @Autowired
    AssetAndLiabilityTab_InputService assetAndLiabilityTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<AssetAndLiabilityTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<AssetAndLiabilityTab_Input> input = assetAndLiabilityTabInputService.findByAppId(appId);
        AssetAndLiabilityTab_Input_Response response = new AssetAndLiabilityTab_Input_Response();
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
    public ResponseEntity<AssetAndLiabilityTab_Input_Response> createAssetAndLiabilityTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody AssetAndLiabilityTab_Input_Request assetAndLiabilityTabInputRequest) {

        assetAndLiabilityTabInputService.createAssetAndLiabilityTabInput(appId, assetAndLiabilityTabInputRequest);
//        AssetAndLiabilityTab_Input newInput = assetAndLiabilityTabInputService.createAssetAndLiabilityTabInput(appId, assetAndLiabilityTabInputRequest);
//        AssetAndLiabilityTab_Input_Response response = new AssetAndLiabilityTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());

        return getData(appId);

    }
}
