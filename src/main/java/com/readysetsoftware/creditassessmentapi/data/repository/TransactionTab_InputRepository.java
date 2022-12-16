package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTab_InputRepository extends JpaRepository<TransactionTab_Input, Integer> {

    Optional<TransactionTab_Input> findByAppId(Integer appId);

}
