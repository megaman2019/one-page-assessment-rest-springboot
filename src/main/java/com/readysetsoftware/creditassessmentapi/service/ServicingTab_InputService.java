package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.ServicingTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ServicingTab_InputService {
    Optional<ServicingTab_Input> findByAppId(Integer appId);
    ServicingTab_Input createServicingTabInput(Integer appId, ServicingTab_Input_Request servicingTabInputRequest);
}
