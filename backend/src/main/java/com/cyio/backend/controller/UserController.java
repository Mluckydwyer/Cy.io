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
        System.out.println("UserID we are search for: " + userPrincipal.getId());
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/deleteuser")
    public @ResponseBody String deleteUser(@RequestParam(value="user") String useridorname, @CurrentUser UserPrincipal principal){
        if (!principal.isAdmin())
            return "unauthorized user";
        userRepository.deleteUserByUserNameOrUserid(useridorname, useridorname);
            return useridorname + " deleted";

    }

    @PostMapping("/toggleadmin")
    public @ResponseBody String toggleAdmin(@RequestParam(value="user") String useridorname, @CurrentUser UserPrincipal principal){
        if (!principal.isAdmin())
            return "unauthorized user";
        List<User> userOp = userRepository.findUserByUserNameOrUserid(useridorname, useridorname);
        if(userOp.isEmpty()){
            return "No such user found";
        }
        for(User u : userOp){
            if (u.isAdmin()) userRepository.updateAdmin(false,useridorname, useridorname);
            else userRepository.updateAdmin(true ,useridorname, useridorname);
        }
        return "Success";
    }
}
