package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExitStrategyTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ExitStrategyTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExitStrategyTab_InputServiceImpl implements ExitStrategyTab_InputService {

    @Autowired
    ExitStrategyTab_InputRepository exitStrategyTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<ExitStrategyTab_Input> findByAppId(Integer appId) {
        return exitStrategyTabInputRepository.findByAppId(appId);
    }

    @Override
    public ExitStrategyTab_Input createExitStrategyTabInput(Integer appId, ExitStrategyTab_Input_Request exitStrategyTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return exitStrategyTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setLoanTermExceedRetAge(exitStrategyTabInputRequest.getLoanTermExceedRetAge());
                        updatedInput.setPolicyException(exitStrategyTabInputRequest.getPolicyException());
                        updatedInput.setNotes(exitStrategyTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(exitStrategyTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(exitStrategyTabInputRequest.getModifiedDate());
                        return exitStrategyTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        ExitStrategyTab_Input newInput = new ExitStrategyTab_Input();
                        newInput.setAppId(exitStrategyTabInputRequest.getAppId());
                        newInput.setLoanTermExceedRetAge(exitStrategyTabInputRequest.getLoanTermExceedRetAge());
                        newInput.setPolicyException(exitStrategyTabInputRequest.getPolicyException());
                        newInput.setNotes(exitStrategyTabInputRequest.getNotes());
                        newInput.setModifiedBy(exitStrategyTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(exitStrategyTabInputRequest.getModifiedDate());
                        return exitStrategyTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
