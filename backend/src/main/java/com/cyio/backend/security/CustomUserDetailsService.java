package com.cyio.backend.security;

import com.cyio.backend.model.User;
import com.cyio.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//this service returns an user's data based on some fields, currently it's by email
//interacts with UserPrincipal
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    /*
        This methods returns the user's data by searching for his/her username
     */
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + userName)
                );

        return UserPrincipal.create(user);
    }

    /*
        This method returns the user's data by searching for his/hers id (this should be the auto generated one)
        This is used by JWTAuthenticationFilter
     */
    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException()
        );

        return UserPrincipal.create(user);
    }
}