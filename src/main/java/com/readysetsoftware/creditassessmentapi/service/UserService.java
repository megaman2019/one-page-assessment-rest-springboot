package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.User;
import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.TransactionTab_Input_Request;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User findByUsername(String username);
}
