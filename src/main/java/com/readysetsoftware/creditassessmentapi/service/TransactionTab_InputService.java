package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.TransactionTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TransactionTab_InputService {
    Optional<TransactionTab_Input> findByAppId(Integer appId);
    TransactionTab_Input createTransactionTabInput(Integer appId, TransactionTab_Input_Request transactionTabInputRequest);
}
