package com.readysetsoftware.creditassessmentapi.data.repository;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.CreditHistory;
import com.readysetsoftware.creditassessmentapi.data.model.Exception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Integer> {

    List<CreditHistory> findByCustIdIn(Integer[] custIdList);
}
