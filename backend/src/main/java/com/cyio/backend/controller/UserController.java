package com.cyio.backend.controller;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.User;
import com.cyio.backend.repository.UserRepository;
import com.cyio.backend.security.CurrentUser;
import com.cyio.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/deleteuser")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody String deleteUser(@RequestParam(value="user") String useridorname){
        if (userRepository.deleteUserByUserNameOrUserid(useridorname, useridorname)){
            return useridorname + "deleted";
        }
        return "Failed to delete";
    }

    @PostMapping("/toggleadmin")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody String toggleAdmin(@RequestParam(value="user") String useridorname){
        List<User> userOp = userRepository.findByUserNameOrUserid(useridorname, useridorname);
        if(userOp.isEmpty()){
            return "No such user found";
        }
        for(User u : userOp){
            if (u.isAdmin()) u.setAdmin(false);
            else u.setAdmin(true);
        }
        return "Success";
    }
}
