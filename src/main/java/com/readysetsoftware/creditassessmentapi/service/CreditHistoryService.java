package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.CreditHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CreditHistoryService {
    List<CreditHistory> findByCustIdIn(Integer[] custIdList);
}




