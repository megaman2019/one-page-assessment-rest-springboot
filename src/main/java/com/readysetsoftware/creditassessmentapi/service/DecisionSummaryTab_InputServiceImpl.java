package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.DecisionSummaryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.DecisionSummaryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.DecisionSummaryTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DecisionSummaryTab_InputServiceImpl implements DecisionSummaryTab_InputService {

    @Autowired
    DecisionSummaryTab_InputRepository decisionSummaryTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<DecisionSummaryTab_Input> findByAppId(Integer appId) {
        return decisionSummaryTabInputRepository.findByAppId(appId);
    }

    @Override
    public DecisionSummaryTab_Input createDecisionSummaryTabInput(Integer appId, DecisionSummaryTab_Input_Request decisionSummaryTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return decisionSummaryTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setDeclineCompleteDate(decisionSummaryTabInputRequest.getDeclineCompleteDate());
                        updatedInput.setMirCompleteDate(decisionSummaryTabInputRequest.getMirCompleteDate());
                        updatedInput.setConditionalCompleteDate(decisionSummaryTabInputRequest.getConditionalCompleteDate());
                        updatedInput.setFormalCompleteDate(decisionSummaryTabInputRequest.getFormalCompleteDate());
                        updatedInput.setPolicyException(decisionSummaryTabInputRequest.getPolicyException());
                        updatedInput.setNotes(decisionSummaryTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(decisionSummaryTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(decisionSummaryTabInputRequest.getModifiedDate());
                        return decisionSummaryTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        DecisionSummaryTab_Input newInput = new DecisionSummaryTab_Input();
                        newInput.setAppId(decisionSummaryTabInputRequest.getAppId());
                        newInput.setDeclineCompleteDate(decisionSummaryTabInputRequest.getDeclineCompleteDate());
                        newInput.setMirCompleteDate(decisionSummaryTabInputRequest.getMirCompleteDate());
                        newInput.setConditionalCompleteDate(decisionSummaryTabInputRequest.getConditionalCompleteDate());
                        newInput.setFormalCompleteDate(decisionSummaryTabInputRequest.getFormalCompleteDate());
                        newInput.setPolicyException(decisionSummaryTabInputRequest.getPolicyException());
                        newInput.setNotes(decisionSummaryTabInputRequest.getNotes());
                        newInput.setModifiedBy(decisionSummaryTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(decisionSummaryTabInputRequest.getModifiedDate());
                        return decisionSummaryTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
