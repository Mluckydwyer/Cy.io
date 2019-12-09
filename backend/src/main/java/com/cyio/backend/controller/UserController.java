package com.cyio.backend.controller;

import com.cyio.backend.model.User;
import com.cyio.backend.security.CurrentUser;
import com.cyio.backend.security.UserPrincipal;
import com.cyio.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        System.out.println("UserID we are search for: " + userPrincipal.getId());
        return userService.getUser(userPrincipal.getId());
    }

    /**
     * :8080/deleteuser?user=USER
     * 	- Status: stable
     * 	- Type: POST
     * 	- Usage: Only available to admin user. The method searhces for the user specified by "USER" where "USER" can be either the username or user id and deletes it
     * 	- Returns: message statingg whether the operation was successful
     *
     * @param useridorname
     * @param principal
     * @return
     */
    @PostMapping("/deleteuser")
    public @ResponseBody String deleteUser(@RequestParam(value="user") String useridorname, @CurrentUser UserPrincipal principal){
        return userService.deleteUser(useridorname, principal);
    }

    /**
     * :8080/toggleadmin?user=USER
     * 	- Status: stable
     * 	- Type: POST
     * 	- Usage: Only available to admin user. This method makes the user specified by "USER" where "USER" can be either the username or user id and make it an admin user if it previously wasn't one and make an admin user into a regular user
     * 	- Returns: message stating whether the operation was successful
     *
     * @param useridorname
     * @param principal
     * @return
     */
    @PostMapping("/toggleadmin")
    public @ResponseBody String toggleAdmin(@RequestParam(value="user") String useridorname, @CurrentUser UserPrincipal principal){
        return userService.toggleAdmin(useridorname, principal);
    }

    /**
     *  :8080/allusers
     *  -Status: Testing
     *  -Type: GET
     *  -Usage: used for the admin user to retrieve all users currently registered in the database.
     *  -Returns: a list of all users
     * @param principal
     * @return
     */
    @GetMapping("/allusers")
    public @ResponseBody List<User> getAllUsers(@CurrentUser UserPrincipal principal){
        if (principal==null || !principal.isAdmin())
          return null;
        else
            return userRepository.findAll();
    }
}
