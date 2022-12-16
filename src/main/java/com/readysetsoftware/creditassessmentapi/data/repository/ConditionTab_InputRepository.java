package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ConditionTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConditionTab_InputRepository extends JpaRepository<ConditionTab_Input, Integer> {

    Optional<ConditionTab_Input> findByAppId(Integer appId);

}
