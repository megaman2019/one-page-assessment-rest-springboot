package com.readysetsoftware.creditassessmentapi.controller;

import com.readysetsoftware.creditassessmentapi.data.model.Application;
import com.readysetsoftware.creditassessmentapi.data.model.User;
import com.readysetsoftware.creditassessmentapi.data.payload.request.JwtAuthRequest;
import com.readysetsoftware.creditassessmentapi.data.payload.response.JwtResponse;
import com.readysetsoftware.creditassessmentapi.data.payload.response.User_Response;
import com.readysetsoftware.creditassessmentapi.data.repository.UserRepository;
import com.readysetsoftware.creditassessmentapi.service.UserService;
import com.readysetsoftware.creditassessmentapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String homePage() {
        return "This is the home page.";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Credentials");
        }
        final String token = jwtUtil.generateToken(jwtAuthRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping(value = "/user/{username}", produces = "application/json")
    public ResponseEntity<User_Response> getUser(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        User_Response userResponse = new User_Response();
        if (user != null) {
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setRole(user.getRole());
        }

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
