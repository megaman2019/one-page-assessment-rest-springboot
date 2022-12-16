package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import com.readysetsoftware.creditassessmentapi.data.repository.ExceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Autowired
    ExceptionRepository exceptionRepository;

    @Override
    public List<Exception> findByAppId(Integer appId) {
        return exceptionRepository.findByAppId(appId);
    }

}
