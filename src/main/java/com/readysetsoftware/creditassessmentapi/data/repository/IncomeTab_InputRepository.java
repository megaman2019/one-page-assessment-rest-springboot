package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.IncomeTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeTab_InputRepository extends JpaRepository<IncomeTab_Input, Integer> {

    Optional<IncomeTab_Input> findByAppId(Integer appId);

}
