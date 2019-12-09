package com.cyio.backend.service;

import com.cyio.backend.exception.ResourceNotFoundException;
import com.cyio.backend.model.User;
import com.cyio.backend.repository.UserRepository;
import com.cyio.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * UserRepository userRepository;
     * @param id
     * @return
     */
    public User getUser(String id){
       return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    /**
     * Only available to admin user. The method searhces for the user specified by "USER" where "USER" can be either the username or user id and deletes it
     *      * 	- Returns: message statingg whether the operation was successful
     *
     * @param idOrUsername
     * @param principal
     * @return
     */
    public String deleteUser(String idOrUsername, UserPrincipal principal){
        if (!principal.isAdmin())
            return "unauthorized user";
        userRepository.deleteUserByUserNameOrUserid(idOrUsername, idOrUsername);
        return idOrUsername + " deleted";
    }

    public String toggleAdmin(String idOrUsername, UserPrincipal principal){
        if (!principal.isAdmin())
            return "unauthorized user";
        List<User> userOp = userRepository.findUserByUserNameOrUserid(idOrUsername, idOrUsername);
        if(userOp.isEmpty()){
            return "No such user found";
        }
        for(User u : userOp){
            if (u.isAdmin()) userRepository.updateAdmin(false,idOrUsername, idOrUsername);
            else userRepository.updateAdmin(true ,idOrUsername, idOrUsername);
        }
        return "Success";
    }

}
