package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ApplicantTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.payload.request.CreditHistoryTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CreditHistoryTab_InputService {
    Optional<CreditHistoryTab_Input> findByAppId(Integer appId);
    CreditHistoryTab_Input createCreditHistoryTabInput(Integer appId, CreditHistoryTab_Input_Request creditHistoryTabInputRequest);
}
