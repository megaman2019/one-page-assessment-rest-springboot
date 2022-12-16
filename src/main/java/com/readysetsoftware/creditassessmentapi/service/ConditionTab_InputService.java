package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ConditionTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ConditionTab_InputService {
    Optional<ConditionTab_Input> findByAppId(Integer appId);
    ConditionTab_Input createConditionTabInput(Integer appId, ConditionTab_Input_Request conditionTabInputRequest);
}
