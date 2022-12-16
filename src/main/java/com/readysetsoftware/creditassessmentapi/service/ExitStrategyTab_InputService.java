package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExitStrategyTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ExitStrategyTab_InputService {
    Optional<ExitStrategyTab_Input> findByAppId(Integer appId);
    ExitStrategyTab_Input createExitStrategyTabInput(Integer appId, ExitStrategyTab_Input_Request exitStrategyTabInputRequest);
}
