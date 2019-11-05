package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatRoom extends AppCompatActivity
{
    private WebSocketClient socket;
    private String name;
    private String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        url = "a";
        try
        {
            socket = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {

                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {

                }
            };
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
//        name = (String)getIntent().getExtras().getString(login.NICKNAME);             <----crashes app
//        socket.

    }
}
