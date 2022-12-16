package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.SecurityTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityTab_InputRepository extends JpaRepository<SecurityTab_Input, Integer> {

    Optional<SecurityTab_Input> findByAppId(Integer appId);

}
