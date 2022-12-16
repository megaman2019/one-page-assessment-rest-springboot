package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantTab_InputRepository extends JpaRepository<ApplicantTab_Input, Integer> {

    Optional<ApplicantTab_Input> findByAppId(Integer appId);

}
