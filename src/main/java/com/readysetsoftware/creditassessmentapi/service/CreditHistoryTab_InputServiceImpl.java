package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.CreditHistoryTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.CreditHistoryTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditHistoryTab_InputServiceImpl implements CreditHistoryTab_InputService {

    @Autowired
    CreditHistoryTab_InputRepository creditHistoryTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<CreditHistoryTab_Input> findByAppId(Integer appId) {
        return creditHistoryTabInputRepository.findByAppId(appId);
    }

    @Override
    public CreditHistoryTab_Input createCreditHistoryTabInput(Integer appId, CreditHistoryTab_Input_Request creditHistoryTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return creditHistoryTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setDirectoryOrBusinessToQuery(creditHistoryTabInputRequest.getDirectoryOrBusinessToQuery());
                        updatedInput.setPolicyException(creditHistoryTabInputRequest.getPolicyException());
                        updatedInput.setNotes(creditHistoryTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(creditHistoryTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(creditHistoryTabInputRequest.getModifiedDate());
                        return creditHistoryTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        CreditHistoryTab_Input newInput = new CreditHistoryTab_Input();
                        newInput.setAppId(creditHistoryTabInputRequest.getAppId());
                        newInput.setDirectoryOrBusinessToQuery(creditHistoryTabInputRequest.getDirectoryOrBusinessToQuery());
                        newInput.setPolicyException(creditHistoryTabInputRequest.getPolicyException());
                        newInput.setNotes(creditHistoryTabInputRequest.getNotes());
                        newInput.setModifiedBy(creditHistoryTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(creditHistoryTabInputRequest.getModifiedDate());
                        return creditHistoryTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
