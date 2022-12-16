package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.DecisionSummaryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExceptionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ExceptionTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExceptionTab_InputServiceImpl implements ExceptionTab_InputService {

    @Autowired
    ExceptionTab_InputRepository exceptionTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

//    @Override
//    public Optional<ExceptionTab_Input> findByAppIdAndTabName(Integer appId, String tabName) {
//        return exceptionTabInputRepository.findByAppIdAndTabName(appId, tabName);
//    }

    @Override
    public ExceptionTab_Input createExceptionTabInput(Integer appId, ExceptionTab_Input_Request exceptionTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            String tabName = exceptionTabInputRequest.getTabName();

            return exceptionTabInputRepository.findByAppIdAndTabName(appId, tabName)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setInput(exceptionTabInputRequest.getInput());
                        updatedInput.setModifiedBy(exceptionTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(exceptionTabInputRequest.getModifiedDate());
                        return exceptionTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        ExceptionTab_Input newInput = new ExceptionTab_Input();
                        newInput.setAppId(exceptionTabInputRequest.getAppId());
                        newInput.setTabName(exceptionTabInputRequest.getTabName());
                        newInput.setInput(exceptionTabInputRequest.getInput());
                        newInput.setModifiedBy(exceptionTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(exceptionTabInputRequest.getModifiedDate());
                        return exceptionTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
