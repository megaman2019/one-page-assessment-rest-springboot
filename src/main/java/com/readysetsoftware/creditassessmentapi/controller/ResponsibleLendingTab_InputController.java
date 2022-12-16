package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ResponsibleLendingTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.response.LivingExpenseTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.data.payload.response.ResponsibleLendingTab_Input_Response;
import com.readysetsoftware.creditassessmentapi.service.ResponsibleLendingTab_InputService;
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
@RequestMapping("/api/responsibleLendingTab_Input")
public class ResponsibleLendingTab_InputController {
    @Autowired
    ResponsibleLendingTab_InputService responsibleLendingTabInputService;

    @GetMapping(value = "/find/{appId}", produces = "application/json")
    public ResponseEntity<ResponsibleLendingTab_Input_Response> getData(@PathVariable("appId") Integer appId) {
        Optional<ResponsibleLendingTab_Input> input = responsibleLendingTabInputService.findByAppId(appId);
        ResponsibleLendingTab_Input_Response response = new ResponsibleLendingTab_Input_Response();
        if (input.isPresent()) {
            response.setId(input.get().getId());
            response.setAppId(input.get().getAppId());
            response.setCostAndRiskOngoingFee(input.get().getCostAndRiskOngoingFee());
            response.setCostAndRiskBreakCosts(input.get().getCostAndRiskBreakCosts());
            response.setCostAndRiskLMI(input.get().getCostAndRiskLMI());
            response.setCostAndRiskRefinanceCosts(input.get().getCostAndRiskRefinanceCosts());
            response.setLoanRepayTypePrincipalAndInterest(input.get().getLoanRepayTypePrincipalAndInterest());
            response.setLoanRepayTypeInterestOnly(input.get().getLoanRepayTypeInterestOnly());
            response.setLoanRepayTypeFixed(input.get().getLoanRepayTypeFixed());
            response.setLoanRepayTypeVariable(input.get().getLoanRepayTypeVariable());
            response.setObjectivePurchase(input.get().getObjectivePurchase());
            response.setObjectiveRefinance(input.get().getObjectiveRefinance());
            response.setObjectiveDebtConsolidation(input.get().getObjectiveDebtConsolidation());
            response.setObjectiveLowInterestRate(input.get().getObjectiveLowInterestRate());
            response.setObjectiveFinancialGoals(input.get().getObjectiveFinancialGoals());
            response.setObjectiveOther(input.get().getObjectiveOther());
            response.setApplicationNotSuitable(input.get().getApplicationNotSuitable());
            response.setApplicationMeetRequirements(input.get().getApplicationMeetRequirements());
            response.setNotes(input.get().getNotes());
            response.setPolicyException(input.get().getPolicyException());
            response.setModifiedBy(input.get().getModifiedBy());
            response.setModifiedDate(input.get().getModifiedDate());
            response.setNoteHistoryList(input.get().getNoteHistoryList());
        }
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @PostMapping("/create/{appId}")
    public ResponseEntity<ResponsibleLendingTab_Input_Response> createResponsibleLendingTabInput(
            @PathVariable("appId") Integer appId,
            @Valid @RequestBody ResponsibleLendingTab_Input_Request responsibleLendingTabInputRequest) {

        responsibleLendingTabInputService.createResponsibleLendingTabInput(appId, responsibleLendingTabInputRequest);
//        ResponsibleLendingTab_Input newInput = responsibleLendingTabInputService.createResponsibleLendingTabInput(appId, responsibleLendingTabInputRequest);
//        ResponsibleLendingTab_Input_Response response = new ResponsibleLendingTab_Input_Response();
//
//        response.setId(newInput.getId());
//        response.setAppId(newInput.getAppId());
//        response.setCostAndRiskOngoingFee(newInput.getCostAndRiskOngoingFee());
//        response.setCostAndRiskBreakCosts(newInput.getCostAndRiskBreakCosts());
//        response.setCostAndRiskLMI(newInput.getCostAndRiskLMI());
//        response.setCostAndRiskRefinanceCosts(newInput.getCostAndRiskRefinanceCosts());
//        response.setLoanRepayTypePrincipalAndInterest(newInput.getLoanRepayTypePrincipalAndInterest());
//        response.setLoanRepayTypeInterestOnly(newInput.getLoanRepayTypeInterestOnly());
//        response.setLoanRepayTypeFixed(newInput.getLoanRepayTypeFixed());
//        response.setLoanRepayTypeVariable(newInput.getLoanRepayTypeVariable());
//        response.setObjectivePurchase(newInput.getObjectivePurchase());
//        response.setObjectiveRefinance(newInput.getObjectiveRefinance());
//        response.setObjectiveDebtConsolidation(newInput.getObjectiveDebtConsolidation());
//        response.setObjectiveLowInterestRate(newInput.getObjectiveLowInterestRate());
//        response.setObjectiveFinancialGoals(newInput.getObjectiveFinancialGoals());
//        response.setObjectiveOther(newInput.getObjectiveOther());
//        response.setApplicationNotSuitable(newInput.getApplicationNotSuitable());
//        response.setApplicationMeetRequirements(newInput.getApplicationMeetRequirements());
//        response.setNotes(newInput.getNotes());
//        response.setPolicyException(newInput.getPolicyException());
//        response.setModifiedBy(newInput.getModifiedBy());
//        response.setModifiedDate(newInput.getModifiedDate());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return getData(appId);

    }
}
