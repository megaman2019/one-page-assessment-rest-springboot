package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.TransactionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.TransactionTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionTab_InputServiceImpl implements TransactionTab_InputService {

    @Autowired
    TransactionTab_InputRepository transactionTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<TransactionTab_Input> findByAppId(Integer appId) {
        return transactionTabInputRepository.findByAppId(appId);
    }

    @Override
    public TransactionTab_Input createTransactionTabInput(Integer appId, TransactionTab_Input_Request transactionTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return transactionTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setRefinanceConductSatisfactory(transactionTabInputRequest.getRefinanceConductSatisfactory());
                        updatedInput.setPolicyException(transactionTabInputRequest.getPolicyException());
                        updatedInput.setNotes(transactionTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(transactionTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(transactionTabInputRequest.getModifiedDate());
                        return transactionTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        TransactionTab_Input newInput = new TransactionTab_Input();
                        newInput.setAppId(transactionTabInputRequest.getAppId());
                        newInput.setRefinanceConductSatisfactory(transactionTabInputRequest.getRefinanceConductSatisfactory());
                        newInput.setPolicyException(transactionTabInputRequest.getPolicyException());
                        newInput.setNotes(transactionTabInputRequest.getNotes());
                        newInput.setModifiedBy(transactionTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(transactionTabInputRequest.getModifiedDate());
                        return transactionTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
