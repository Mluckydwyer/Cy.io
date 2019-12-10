package com.cyio.backend.service;

import com.cyio.backend.exception.BadRequestException;
import com.cyio.backend.model.AuthProvider;
import com.cyio.backend.model.User;
import com.cyio.backend.payload.ApiResponse;
import com.cyio.backend.payload.AuthResponse;
import com.cyio.backend.payload.LoginRequest;
import com.cyio.backend.payload.SignUpRequest;
import com.cyio.backend.repository.UserRepository;
import com.cyio.backend.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     *uses the Springboot authenticationManager to check if the username password combo exists
     *
     * @param request
     * @return
     */
    public ResponseEntity<?> authenticateUser(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getuserName(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //if log in was successful, it uses the token provider class to generate a token secret
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * creates a new user if it does not exist already
     * @param request
     * @return
     */
    public ResponseEntity<?> registerUser(SignUpRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getUserid()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
}
