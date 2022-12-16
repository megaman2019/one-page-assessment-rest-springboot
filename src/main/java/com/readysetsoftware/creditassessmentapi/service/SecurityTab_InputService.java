package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.SecurityTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SecurityTab_InputService {
    Optional<SecurityTab_Input> findByAppId(Integer appId);
    SecurityTab_Input createSecurityTabInput(Integer appId, SecurityTab_Input_Request securityTabInputRequest);
}
