package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ApplicantTab_InputService {
    Optional<ApplicantTab_Input> findByAppId(Integer appId);
    ApplicantTab_Input createApplicantTabInput(Integer appId, ApplicantTab_Input_Request applicantTabInputRequest);
}
