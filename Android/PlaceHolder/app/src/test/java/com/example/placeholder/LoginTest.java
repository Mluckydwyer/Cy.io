package com.example.placeholder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
        loginActivity.setUser(null);
        assertEquals(null, loginActivity.getUser());
    }
}
