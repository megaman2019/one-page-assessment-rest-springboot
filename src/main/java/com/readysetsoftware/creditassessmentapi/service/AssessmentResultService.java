package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.AssessmentResult;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssessmentResultRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AssessmentResultService {

    AssessmentResult createAssessmentResult(AssessmentResultRequest assessmentResultRequest);
    Optional<AssessmentResult> updateAssessmentResult(Integer id, AssessmentResultRequest assessmentResultRequest);
    void deleteAssessmentResult(Integer id);
    AssessmentResult getSingleAssessmentResult(Integer id);
    List<AssessmentResult> findAllAssessmentResult();

}
