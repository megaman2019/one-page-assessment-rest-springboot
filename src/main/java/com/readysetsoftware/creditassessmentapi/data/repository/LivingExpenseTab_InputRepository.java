package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.LivingExpenseTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivingExpenseTab_InputRepository extends JpaRepository<LivingExpenseTab_Input, Integer> {

    Optional<LivingExpenseTab_Input> findByAppId(Integer appId);

}
