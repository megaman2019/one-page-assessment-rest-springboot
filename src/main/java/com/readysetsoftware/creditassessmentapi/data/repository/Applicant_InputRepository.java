package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import com.readysetsoftware.creditassessmentapi.data.model.input.Applicant_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Applicant_InputRepository extends JpaRepository<Applicant_Input, Integer> {

    List<Applicant_Input> findByAppId(Integer appId);
    Optional<Applicant_Input> findByApplicantId(Integer applicantId);

}
