package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.IncomeTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IncomeTab_InputService {
    Optional<IncomeTab_Input> findByAppId(Integer appId);
    IncomeTab_Input createIncomeTabInput(Integer appId, IncomeTab_Input_Request incomeTabInputRequest);
}
