package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.IncomeTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.IncomeTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeTab_InputServiceImpl implements IncomeTab_InputService {

    @Autowired
    IncomeTab_InputRepository incomeTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<IncomeTab_Input> findByAppId(Integer appId) {
        return incomeTabInputRepository.findByAppId(appId);
    }

    @Override
    public IncomeTab_Input createIncomeTabInput(Integer appId, IncomeTab_Input_Request incomeTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return incomeTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setPolicyException(incomeTabInputRequest.getPolicyException());
                        updatedInput.setNotes(incomeTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(incomeTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(incomeTabInputRequest.getModifiedDate());
                        return incomeTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        IncomeTab_Input newInput = new IncomeTab_Input();
                        newInput.setAppId(incomeTabInputRequest.getAppId());
                        newInput.setPolicyException(incomeTabInputRequest.getPolicyException());
                        newInput.setNotes(incomeTabInputRequest.getNotes());
                        newInput.setModifiedBy(incomeTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(incomeTabInputRequest.getModifiedDate());
                        return incomeTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
