package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ExceptionTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ExceptionTab_InputService {

//    Optional<ExceptionTab_Input> findByAppIdAndTabName(Integer appId, String tabName);
    ExceptionTab_Input createExceptionTabInput(Integer appId, ExceptionTab_Input_Request exceptionTabInputRequest);
}
