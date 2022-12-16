package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ConditionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.CreditHistoryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ConditionTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.CreditHistoryTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConditionTab_InputServiceImpl implements ConditionTab_InputService {

    @Autowired
    ConditionTab_InputRepository conditionTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<ConditionTab_Input> findByAppId(Integer appId) {
        return conditionTabInputRepository.findByAppId(appId);
    }

    @Override
    public ConditionTab_Input createConditionTabInput(Integer appId, ConditionTab_Input_Request conditionTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return conditionTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setPolicyException(conditionTabInputRequest.getPolicyException());
                        updatedInput.setNotes(conditionTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(conditionTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(conditionTabInputRequest.getModifiedDate());
                        return conditionTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        ConditionTab_Input newInput = new ConditionTab_Input();
                        newInput.setAppId(conditionTabInputRequest.getAppId());
                        newInput.setPolicyException(conditionTabInputRequest.getPolicyException());
                        newInput.setNotes(conditionTabInputRequest.getNotes());
                        newInput.setModifiedBy(conditionTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(conditionTabInputRequest.getModifiedDate());
                        return conditionTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
