package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.AssessmentResult;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.AssessmentResultRequest;
import com.readysetsoftware.creditassessmentapi.data.repository.AssessmentResultRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentResultServiceImpl implements AssessmentResultService{

    @Autowired
    AssessmentResultRepository assessmentResultRepository;

    @Override
    public AssessmentResult createAssessmentResult(AssessmentResultRequest assessmentResultRequest){

        try {
            AssessmentResult newAssessmentResult = new AssessmentResult();

            newAssessmentResult.setRule(assessmentResultRequest.getRule());
            newAssessmentResult.setAppValue(assessmentResultRequest.getAppValue());
            newAssessmentResult.setRuleValue(assessmentResultRequest.getRuleValue());
            newAssessmentResult.setResult(assessmentResultRequest.getResult());

            assessmentResultRepository.save(newAssessmentResult);
            return newAssessmentResult;

        } catch (Exception ex) {
            throw new ApiRequestException("Create failed: " + ex.getMessage());
        }

    }

    @Override
    public Optional<AssessmentResult> updateAssessmentResult(Integer id, AssessmentResultRequest assessmentResultRequest) {
        Optional<AssessmentResult> assessmentResult = assessmentResultRepository.findById(id);
        if (assessmentResult.isPresent()) {

            assessmentResult.get().setRule(assessmentResultRequest.getRule());
            assessmentResult.get().setAppValue(assessmentResultRequest.getAppValue());
            assessmentResult.get().setRuleValue(assessmentResultRequest.getRuleValue());
            assessmentResult.get().setResult(assessmentResultRequest.getResult());

            assessmentResultRepository.save(assessmentResult.get());

        } else {
            throw new ApiRequestException("Update failed. Assessment result does not exist for the id " + id);
        }

        return assessmentResult;
    }

    @Override
    public AssessmentResult getSingleAssessmentResult(Integer id) {
        return assessmentResultRepository
                .findById(id)
                .orElseThrow(()-> new ApiRequestException("No assessment result found for id: " + id));
    }

    @Override
    public List<AssessmentResult> findAllAssessmentResult() {
        final String type = "CA";
        return assessmentResultRepository.findByType(type);
    }

    @Override
    public void deleteAssessmentResult(Integer id) {

        try{
            assessmentResultRepository.deleteById(id);
        } catch (Exception ex) {
            //throw new CustomException(String.format("Delete failed. Id %s not found.", id));
            throw new ApiRequestException(String.format(ex.getLocalizedMessage(), id));
        }
    }


}
