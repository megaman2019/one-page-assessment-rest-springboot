package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExceptionService {
    List<Exception> findByAppId(Integer appId);
}
