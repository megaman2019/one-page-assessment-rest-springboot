package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import com.readysetsoftware.creditassessmentapi.data.model.input.ApplicantTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExceptionRepository extends JpaRepository<Exception, Integer> {

    List<Exception> findByAppId(Integer appId);

}
