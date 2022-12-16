package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
}
