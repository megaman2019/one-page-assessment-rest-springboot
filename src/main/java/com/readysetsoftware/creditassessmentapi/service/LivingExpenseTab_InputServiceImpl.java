package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.LivingExpenseTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.LivingExpenseTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivingExpenseTab_InputServiceImpl implements LivingExpenseTab_InputService {

    @Autowired
    LivingExpenseTab_InputRepository livingExpenseTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<LivingExpenseTab_Input> findByAppId(Integer appId) {
        return livingExpenseTabInputRepository.findByAppId(appId);
    }

    @Override
    public LivingExpenseTab_Input createLivingExpenseTabInput(Integer appId, LivingExpenseTab_Input_Request livingExpenseTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return livingExpenseTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setDleLesserThanHem(livingExpenseTabInputRequest.getDleLesserThanHem());
                        updatedInput.setHem(livingExpenseTabInputRequest.getHem());
                        updatedInput.setPolicyException(livingExpenseTabInputRequest.getPolicyException());
                        updatedInput.setNotes(livingExpenseTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(livingExpenseTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(livingExpenseTabInputRequest.getModifiedDate());
                        return livingExpenseTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        LivingExpenseTab_Input newInput = new LivingExpenseTab_Input();
                        newInput.setAppId(livingExpenseTabInputRequest.getAppId());
                        newInput.setHem(livingExpenseTabInputRequest.getHem());
                        newInput.setDleLesserThanHem(livingExpenseTabInputRequest.getDleLesserThanHem());
                        newInput.setPolicyException(livingExpenseTabInputRequest.getPolicyException());
                        newInput.setNotes(livingExpenseTabInputRequest.getNotes());
                        newInput.setModifiedBy(livingExpenseTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(livingExpenseTabInputRequest.getModifiedDate());
                        return livingExpenseTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
