package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {
    List<Application> getAllApplication();
    Application getApplicationById(Integer id);
}
