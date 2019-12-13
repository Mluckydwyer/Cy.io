package com.example.placeholder;

import com.example.placeholder.Activities.ChatRoomActivity;
import com.example.placeholder.Models.Message;

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
public class ChatRoomTest
{
    @Mock
    ChatRoomActivity chatRoomActivity;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void message_test()
    {
        Message m = new Message("chuck", "whats up");
        assertEquals(m.getName(), "chuck");
        assertEquals(m.getText(), "whats up");
    }

    @Test
    public void message_test2()
    {
        Message m = new Message("chuck", "whats up");
        m.setName("peter");
        m.setText("banana");
        assertNotEquals(m.getName(), "chuck");
        assertNotEquals(m.getText(), "whats up");
        assertEquals(m.getName(), "peter");
        assertEquals(m.getText(), "banana");
    }

    @Test
    public void nullMessage_test()
    {
        Message m = new Message();
        assertEquals(m.getText(), null);
        assertEquals(m.getName(), null);
    }
}
