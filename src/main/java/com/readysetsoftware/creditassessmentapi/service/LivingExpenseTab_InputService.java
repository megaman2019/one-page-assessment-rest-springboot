package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.LivingExpenseTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LivingExpenseTab_InputService {
    Optional<LivingExpenseTab_Input> findByAppId(Integer appId);
    LivingExpenseTab_Input createLivingExpenseTabInput(Integer appId, LivingExpenseTab_Input_Request livingExpenseTabInputRequest);
}
