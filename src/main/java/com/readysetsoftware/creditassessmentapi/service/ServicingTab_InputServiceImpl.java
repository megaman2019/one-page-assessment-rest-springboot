package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ServicingTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.ServicingTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicingTab_InputServiceImpl implements ServicingTab_InputService {

    @Autowired
    ServicingTab_InputRepository servicingTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<ServicingTab_Input> findByAppId(Integer appId) {
        return servicingTabInputRepository.findByAppId(appId);
    }

    @Override
    public ServicingTab_Input createServicingTabInput(Integer appId, ServicingTab_Input_Request servicingTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return servicingTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {

                        updatedInput.setNsr(servicingTabInputRequest.getNsr());
                        updatedInput.setNms(servicingTabInputRequest.getNms());
                        updatedInput.setNas(servicingTabInputRequest.getNas());
                        updatedInput.setDti(servicingTabInputRequest.getDti());
                        updatedInput.setLti(servicingTabInputRequest.getLti());
                        updatedInput.setSubordination(servicingTabInputRequest.getSubordination());
                        updatedInput.setPolicyException(servicingTabInputRequest.getPolicyException());
                        updatedInput.setNotes(servicingTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(servicingTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(servicingTabInputRequest.getModifiedDate());
                        updatedInput.setLastCreditEvent(servicingTabInputRequest.getLastCreditEvent());
                        updatedInput.setNoOfCreditEvents(servicingTabInputRequest.getNoOfCreditEvents());

                        return servicingTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {

                        ServicingTab_Input newInput = new ServicingTab_Input();

                        newInput.setAppId(servicingTabInputRequest.getAppId());
                        newInput.setNsr(servicingTabInputRequest.getNsr());
                        newInput.setNms(servicingTabInputRequest.getNms());
                        newInput.setNas(servicingTabInputRequest.getNas());
                        newInput.setDti(servicingTabInputRequest.getDti());
                        newInput.setLti(servicingTabInputRequest.getLti());
                        newInput.setSubordination(servicingTabInputRequest.getSubordination());
                        newInput.setPolicyException(servicingTabInputRequest.getPolicyException());
                        newInput.setNotes(servicingTabInputRequest.getNotes());
                        newInput.setModifiedBy(servicingTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(servicingTabInputRequest.getModifiedDate());
                        newInput.setLastCreditEvent(servicingTabInputRequest.getLastCreditEvent());
                        newInput.setNoOfCreditEvents(servicingTabInputRequest.getNoOfCreditEvents());

                        return servicingTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
