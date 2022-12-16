package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.Applicant_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.Applicant_Input_Request;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface Applicant_InputService {
    List<Applicant_Input> findByAppId(Integer appId);
    Applicant_Input createApplicantInput(Integer appId, Applicant_Input_Request applicantInputRequest);
}
