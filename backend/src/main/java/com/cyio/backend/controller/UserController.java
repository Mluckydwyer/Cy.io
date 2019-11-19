package com.cyio.backend.controller;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.User;
import com.cyio.backend.repository.UserRepository;
import com.cyio.backend.security.CurrentUser;
import com.cyio.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("UserID we are search for: " + userPrincipal.getId());
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
