package com.readysetsoftware.creditassessmentapi.service;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.User;
import com.readysetsoftware.creditassessmentapi.data.model.input.TransactionTab_Input;
import com.readysetsoftware.creditassessmentapi.data.payload.request.TransactionTab_Input_Request;
import com.readysetsoftware.creditassessmentapi.data.repository.ApplicationRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.TransactionTab_InputRepository;
import com.readysetsoftware.creditassessmentapi.data.repository.UserRepository;
import com.readysetsoftware.creditassessmentapi.exception.ApiRequestException;
import com.readysetsoftware.creditassessmentapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
