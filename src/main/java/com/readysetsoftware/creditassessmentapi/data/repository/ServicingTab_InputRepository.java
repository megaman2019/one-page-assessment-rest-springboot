package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.ServicingTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicingTab_InputRepository extends JpaRepository<ServicingTab_Input, Integer> {

    Optional<ServicingTab_Input> findByAppId(Integer appId);

}
