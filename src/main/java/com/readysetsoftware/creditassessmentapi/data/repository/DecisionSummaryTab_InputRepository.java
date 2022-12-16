package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.DecisionSummaryTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DecisionSummaryTab_InputRepository extends JpaRepository<DecisionSummaryTab_Input, Integer>{

    Optional<DecisionSummaryTab_Input> findByAppId(Integer appId);

}
