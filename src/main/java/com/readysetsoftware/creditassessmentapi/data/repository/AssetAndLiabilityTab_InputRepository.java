package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.input.AssetAndLiabilityTab_Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetAndLiabilityTab_InputRepository extends JpaRepository<AssetAndLiabilityTab_Input, Integer> {

    Optional<AssetAndLiabilityTab_Input> findByAppId(Integer appId);

}
