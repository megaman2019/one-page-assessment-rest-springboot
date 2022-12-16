package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.DecisionSummaryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.DecisionSummaryTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DecisionSummaryTab_InputService {
    Optional<DecisionSummaryTab_Input> findByAppId(Integer appId);
    DecisionSummaryTab_Input createDecisionSummaryTabInput(Integer appId, DecisionSummaryTab_Input_Request decisionSummaryTabInputRequest);
}
