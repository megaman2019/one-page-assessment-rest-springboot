package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.CreditHistoryTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditHistoryTab_InputRepository extends JpaRepository<CreditHistoryTab_Input, Integer> {

    Optional<CreditHistoryTab_Input> findByAppId(Integer appId);

}
