package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.CreditHistory;
import com.readysetsoftware.creditassessmentapi.data.repository.CreditHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditHistoryServiceImpl implements CreditHistoryService {

    @Autowired
    CreditHistoryRepository creditHistoryRepository;

    @Override
    public List<CreditHistory> findByCustIdIn(Integer[] custIdList) {
        return creditHistoryRepository.findByCustIdIn(custIdList);
    }
}
