package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssetAndLiabilityTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.AssetAndLiabilityTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetAndLiabilityTab_InputServiceImpl implements AssetAndLiabilityTab_InputService {

    @Autowired
    AssetAndLiabilityTab_InputRepository assetAndLiabilityTabInputRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Optional<AssetAndLiabilityTab_Input> findByAppId(Integer appId) {
        return assetAndLiabilityTabInputRepository.findByAppId(appId);
    }

    @Override
    public AssetAndLiabilityTab_Input createAssetAndLiabilityTabInput(Integer appId, AssetAndLiabilityTab_Input_Request assetAndLiabilityTabInputRequest) {

        try{

            Application application = applicationRepository.findById(appId)
                    .orElseThrow(() -> new ResourceNotFoundException("No Application found for id : " + appId));

            return assetAndLiabilityTabInputRepository.findByAppId(appId)
                    // if exist then update
                    .map(updatedInput -> {
                        updatedInput.setPolicyException(assetAndLiabilityTabInputRequest.getPolicyException());
                        updatedInput.setNotes(assetAndLiabilityTabInputRequest.getNotes());
                        updatedInput.setModifiedBy(assetAndLiabilityTabInputRequest.getModifiedBy());
                        updatedInput.setModifiedDate(assetAndLiabilityTabInputRequest.getModifiedDate());
                        return assetAndLiabilityTabInputRepository.save(updatedInput);
                    })
                    // if note does not exist then create
                    .orElseGet(() -> {
                        AssetAndLiabilityTab_Input newInput = new AssetAndLiabilityTab_Input();
                        newInput.setAppId(assetAndLiabilityTabInputRequest.getAppId());
                        newInput.setPolicyException(assetAndLiabilityTabInputRequest.getPolicyException());
                        newInput.setNotes(assetAndLiabilityTabInputRequest.getNotes());
                        newInput.setModifiedBy(assetAndLiabilityTabInputRequest.getModifiedBy());
                        newInput.setModifiedDate(assetAndLiabilityTabInputRequest.getModifiedDate());
                        return assetAndLiabilityTabInputRepository.save(newInput);
                    });

        } catch (Exception e) {

            throw new ApiRequestException(e.getMessage());

        }
    }
}
