package com.example.placeholder;

import com.example.placeholder.Activities.LoginActivity;
import com.example.placeholder.Models.Message;
import com.example.placeholder.SupportingClasses.LoginSupport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class LoginTest
{
    @Mock
    LoginActivity loginActivity;

    @Mock
    LoginSupport loginSupport;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void userTest()
    {
        loginActivity.user = "chuck";
        assertEquals("chuck", loginActivity.user);
    }

    @Test
    public void null_UserTest()
    {
        loginSupport.setUser(null);
        assertEquals(null, loginSupport.getUser());
    }

    @Test
    public void null_PasswordTest()
    {
        loginSupport.setPass(null);
        assertEquals(null, loginSupport.getPass());
    }

    @Test
    public void passwordTest()
    {
        loginSupport.setPass("password");
        String pass = "password";
        when(loginSupport.getPass()).thenReturn(pass);
        assertEquals(loginSupport.getPass(), "password");
    }
}
