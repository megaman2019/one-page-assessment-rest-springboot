package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.CreditHistory;
import com.readysetsoftware.creditassessmentapi.service.CreditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@Validated
@RequestMapping("/api/creditHistory")
public class CreditHistoryController {

    @Autowired
    CreditHistoryService creditHistoryService;

    @GetMapping(value = "/find/{custIdList}", produces = "application/json")
    public ResponseEntity<List<CreditHistory>> getData(@PathVariable("custIdList") Integer[] custIdList) {
        List<CreditHistory> creditHistories = creditHistoryService.findByCustIdIn(custIdList);
        return new ResponseEntity<> (creditHistories, HttpStatus.OK);
    }

}
