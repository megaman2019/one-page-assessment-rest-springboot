package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExceptionTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExceptionTab_InputRepository extends JpaRepository<ExceptionTab_Input, Integer> {

    Optional<ExceptionTab_Input> findByAppIdAndTabName(Integer appId, String tabName);

}
