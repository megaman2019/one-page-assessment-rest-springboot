package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ResponsibleLendingTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsibleLendingTab_InputRepository extends JpaRepository<ResponsibleLendingTab_Input, Integer> {

    Optional<ResponsibleLendingTab_Input> findByAppId(Integer appId);

}
