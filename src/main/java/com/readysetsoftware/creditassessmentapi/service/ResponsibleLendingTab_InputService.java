package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ResponsibleLendingTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ResponsibleLendingTab_InputService {
    Optional<ResponsibleLendingTab_Input> findByAppId(Integer appId);
    ResponsibleLendingTab_Input createResponsibleLendingTabInput(Integer appId, ResponsibleLendingTab_Input_Request responsibleLendingTabInputRequest);
}
