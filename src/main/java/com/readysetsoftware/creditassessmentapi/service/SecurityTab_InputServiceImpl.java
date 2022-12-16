package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.SecurityTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.SecurityTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityTab_InputServiceImpl implements SecurityTab_InputService {

    @Autowired
    SecurityTab_InputRepository securityTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<SecurityTab_Input> findByAppId(Integer appId) {
        return securityTabInputRepository.findByAppId(appId);
    }

    @Override
    public SecurityTab_Input createSecurityTabInput(Integer appId, SecurityTab_Input_Request securityTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return securityTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setPolicyException(securityTabInputRequest.getPolicyException());
                        updatedInput.setNotes(securityTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(securityTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(securityTabInputRequest.getModifiedDate());

                        return securityTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        SecurityTab_Input newInput = new SecurityTab_Input();
                        newInput.setAppId(securityTabInputRequest.getAppId());
                        newInput.setPolicyException(securityTabInputRequest.getPolicyException());
                        newInput.setNotes(securityTabInputRequest.getNotes());
                        newInput.setModifiedBy(securityTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(securityTabInputRequest.getModifiedDate());

                        return securityTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
