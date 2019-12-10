package com.cyio.backend.controller;

import com.cyio.backend.payload.LoginRequest;
import com.cyio.backend.payload.SignUpRequest;
import com.cyio.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * This method handles the login process. If success, it returns the token for the curent user session. If failed, then it would return the error message
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
      return authService.authenticateUser(loginRequest);
    }

    /**
     * creates a new user if it does not exist already
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
      return authService.registerUser(signUpRequest);
    }
}
