package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ResponsibleLendingTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ResponsibleLendingTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponsibleLendingTab_InputServiceImpl implements ResponsibleLendingTab_InputService {

    @Autowired
    ResponsibleLendingTab_InputRepository responsibleLendingTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<ResponsibleLendingTab_Input> findByAppId(Integer appId) {
        return responsibleLendingTabInputRepository.findByAppId(appId);
    }

    @Override
    public ResponsibleLendingTab_Input createResponsibleLendingTabInput(Integer appId, ResponsibleLendingTab_Input_Request responsibleLendingTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return responsibleLendingTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setCostAndRiskOngoingFee(responsibleLendingTabInputRequest.getCostAndRiskOngoingFee());
                        updatedInput.setCostAndRiskBreakCosts(responsibleLendingTabInputRequest.getCostAndRiskBreakCosts());
                        updatedInput.setCostAndRiskLMI(responsibleLendingTabInputRequest.getCostAndRiskLMI());
                        updatedInput.setCostAndRiskRefinanceCosts(responsibleLendingTabInputRequest.getCostAndRiskRefinanceCosts());
                        updatedInput.setLoanRepayTypePrincipalAndInterest(responsibleLendingTabInputRequest.getLoanRepayTypePrincipalAndInterest());
                        updatedInput.setLoanRepayTypeInterestOnly(responsibleLendingTabInputRequest.getLoanRepayTypeInterestOnly());
                        updatedInput.setLoanRepayTypeFixed(responsibleLendingTabInputRequest.getLoanRepayTypeFixed());
                        updatedInput.setLoanRepayTypeVariable(responsibleLendingTabInputRequest.getLoanRepayTypeVariable());
                        updatedInput.setObjectivePurchase(responsibleLendingTabInputRequest.getObjectivePurchase());
                        updatedInput.setObjectiveRefinance(responsibleLendingTabInputRequest.getObjectiveRefinance());
                        updatedInput.setObjectiveDebtConsolidation(responsibleLendingTabInputRequest.getObjectiveDebtConsolidation());
                        updatedInput.setObjectiveLowInterestRate(responsibleLendingTabInputRequest.getObjectiveLowInterestRate());
                        updatedInput.setObjectiveFinancialGoals(responsibleLendingTabInputRequest.getObjectiveFinancialGoals());
                        updatedInput.setObjectiveOther(responsibleLendingTabInputRequest.getObjectiveOther());
                        updatedInput.setApplicationNotSuitable(responsibleLendingTabInputRequest.getApplicationNotSuitable());
                        updatedInput.setApplicationMeetRequirements(responsibleLendingTabInputRequest.getApplicationMeetRequirements());
                        updatedInput.setPolicyException(responsibleLendingTabInputRequest.getPolicyException());
                        updatedInput.setNotes(responsibleLendingTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(responsibleLendingTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(responsibleLendingTabInputRequest.getModifiedDate());

                        return responsibleLendingTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        ResponsibleLendingTab_Input newInput = new ResponsibleLendingTab_Input();
                        newInput.setAppId(responsibleLendingTabInputRequest.getAppId());
                        newInput.setCostAndRiskOngoingFee(responsibleLendingTabInputRequest.getCostAndRiskOngoingFee());
                        newInput.setCostAndRiskBreakCosts(responsibleLendingTabInputRequest.getCostAndRiskBreakCosts());
                        newInput.setCostAndRiskLMI(responsibleLendingTabInputRequest.getCostAndRiskLMI());
                        newInput.setCostAndRiskRefinanceCosts(responsibleLendingTabInputRequest.getCostAndRiskRefinanceCosts());
                        newInput.setLoanRepayTypePrincipalAndInterest(responsibleLendingTabInputRequest.getLoanRepayTypePrincipalAndInterest());
                        newInput.setLoanRepayTypeInterestOnly(responsibleLendingTabInputRequest.getLoanRepayTypeInterestOnly());
                        newInput.setLoanRepayTypeFixed(responsibleLendingTabInputRequest.getLoanRepayTypeFixed());
                        newInput.setLoanRepayTypeVariable(responsibleLendingTabInputRequest.getLoanRepayTypeVariable());
                        newInput.setObjectivePurchase(responsibleLendingTabInputRequest.getObjectivePurchase());
                        newInput.setObjectiveRefinance(responsibleLendingTabInputRequest.getObjectiveRefinance());
                        newInput.setObjectiveDebtConsolidation(responsibleLendingTabInputRequest.getObjectiveDebtConsolidation());
                        newInput.setObjectiveLowInterestRate(responsibleLendingTabInputRequest.getObjectiveLowInterestRate());
                        newInput.setObjectiveFinancialGoals(responsibleLendingTabInputRequest.getObjectiveFinancialGoals());
                        newInput.setObjectiveOther(responsibleLendingTabInputRequest.getObjectiveOther());
                        newInput.setApplicationNotSuitable(responsibleLendingTabInputRequest.getApplicationNotSuitable());
                        newInput.setApplicationMeetRequirements(responsibleLendingTabInputRequest.getApplicationMeetRequirements());
                        newInput.setPolicyException(responsibleLendingTabInputRequest.getPolicyException());
                        newInput.setNotes(responsibleLendingTabInputRequest.getNotes());
                        newInput.setModifiedBy(responsibleLendingTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(responsibleLendingTabInputRequest.getModifiedDate());

                        return responsibleLendingTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
