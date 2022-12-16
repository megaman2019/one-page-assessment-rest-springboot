package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicantTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicantTab_InputServiceImpl implements ApplicantTab_InputService {

    @Autowired
    ApplicantTab_InputRepository applicantTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<ApplicantTab_Input> findByAppId(Integer appId) {
        return applicantTabInputRepository.findByAppId(appId);
    }

    @Override
    public ApplicantTab_Input createApplicantTabInput(Integer appId, ApplicantTab_Input_Request applicantTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return applicantTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setPolicyException(applicantTabInputRequest.getPolicyException());
                        updatedInput.setNotes(applicantTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(applicantTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(applicantTabInputRequest.getModifiedDate());
                        return applicantTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        ApplicantTab_Input newInput = new ApplicantTab_Input();
                        newInput.setAppId(applicantTabInputRequest.getAppId());
                        newInput.setPolicyException(applicantTabInputRequest.getPolicyException());
                        newInput.setNotes(applicantTabInputRequest.getNotes());
                        newInput.setModifiedBy(applicantTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(applicantTabInputRequest.getModifiedDate());
                        return applicantTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
