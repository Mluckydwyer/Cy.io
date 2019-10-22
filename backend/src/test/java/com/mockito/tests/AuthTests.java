package com.mockito.tests;

import com.cyio.backend.controller.AuthController;
import com.cyio.backend.exception.BadRequestException;
import com.cyio.backend.model.User;
import com.cyio.backend.payload.LoginRequest;
import com.cyio.backend.payload.SignUpRequest;
import com.cyio.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class AuthTests {
    @InjectMocks
    AuthController controller;

    @Mock
    UserRepository userRepo;

    @Mock
    PasswordEncoder encoder;

    @Mock
    ServletUriComponentsBuilder builder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void LoginTest(){
        User u1 = new User();
        u1.setUserName("test_user");
        u1.setEmail("u1@cy.io");
        u1.setPassword("password");

        Optional<User> ans = Optional.of(u1);

        when(userRepo.findByUserName("test_user")).thenReturn(ans);

        LoginRequest request = new LoginRequest();
        request.setUserName("test_user");
        request.setPassword("password");
       // ResponseEntity response = controller.authenticateUser(request);


    }

    @Test
    public void signUpTest(){
        User u1 = new User();
        u1.setUserName("test_user");
        u1.setEmail("u1@cy.io");
        u1.setPassword("password");

        SignUpRequest request = new SignUpRequest();
        request.setEmail("u1@cy.io");
        request.setUserName("test_user");
        request.setPassword("password");

        when(userRepo.save(new User())).thenReturn(u1);
        when(userRepo.existsByEmail("u1@cy.io")).thenReturn(false);
        when(encoder.encode("password")).thenReturn("password");

        ResponseEntity response = controller.registerUser(request);

    }


   @Test(expected = BadRequestException.class)
   public void checkDuplicateTest(){
       User u1 = new User();
       u1.setUserName("test_user");
       u1.setEmail("u1@cy.io");
       u1.setPassword("password");

       SignUpRequest request = new SignUpRequest();
       request.setEmail("u1@cy.io");
       request.setUserName("test_user");
       request.setPassword("password");

       when(userRepo.save(new User())).thenReturn(u1);
       when(userRepo.existsByEmail("u1@cy.io")).thenReturn(true);

       ResponseEntity response = controller.registerUser(request);
   }
}
