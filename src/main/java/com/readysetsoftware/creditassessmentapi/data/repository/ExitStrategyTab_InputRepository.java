package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ExitStrategyTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExitStrategyTab_InputRepository extends JpaRepository<ExitStrategyTab_Input, Integer> {

    Optional<ExitStrategyTab_Input> findByAppId(Integer appId);

}
