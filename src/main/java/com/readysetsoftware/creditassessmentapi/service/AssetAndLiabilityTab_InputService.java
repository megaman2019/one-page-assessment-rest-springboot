package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssetAndLiabilityTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AssetAndLiabilityTab_InputService {
    Optional<AssetAndLiabilityTab_Input> findByAppId(Integer appId);
    AssetAndLiabilityTab_Input createAssetAndLiabilityTabInput(Integer appId, AssetAndLiabilityTab_Input_Request assetAndLiabilityTabInputRequest);
}
