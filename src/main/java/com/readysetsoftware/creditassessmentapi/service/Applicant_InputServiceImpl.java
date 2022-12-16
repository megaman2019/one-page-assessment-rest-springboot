package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.Applicant_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.Applicant_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.Applicant_InputRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ExceptionRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Applicant_InputServiceImpl implements Applicant_InputService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    Applicant_InputRepository applicantInputRepository;

    @Override
    public List<Applicant_Input> findByAppId(Integer appId) {
        return applicantInputRepository.findByAppId(appId);
    }

    @Override
    public Applicant_Input createApplicantInput(Integer appId, Applicant_Input_Request applicantInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            Integer applicantId = applicantInputRequest.getApplicantId();

            return applicantInputRepository.findByApplicantId(applicantId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setAppId(applicantInputRequest.getAppId());
                        updatedInput.setApplicantId(applicantInputRequest.getApplicantId());
                        updatedInput.setMatrixIdPass(applicantInputRequest.getMatrixIdPass());
                        updatedInput.setPepSanctionMatch(applicantInputRequest.getPepSanctionMatch());
                        updatedInput.setModifiedBy(applicantInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(applicantInputRequest.getModifiedDate());
                        return applicantInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        Applicant_Input newInput = new Applicant_Input();
                        newInput.setAppId(applicantInputRequest.getAppId());
                        newInput.setApplicantId(applicantInputRequest.getApplicantId());
                        newInput.setMatrixIdPass(applicantInputRequest.getMatrixIdPass());
                        newInput.setPepSanctionMatch(applicantInputRequest.getPepSanctionMatch());
                        newInput.setModifiedBy(applicantInputRequest.getModifiedBy());
                        newInput.setModifiedDate(applicantInputRequest.getModifiedDate());
                        return applicantInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
