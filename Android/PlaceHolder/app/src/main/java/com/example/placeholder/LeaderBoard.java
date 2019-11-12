package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class LeaderBoard extends AppCompatActivity
{

    private WebSocketClient socket;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        url = "http://coms-309-nv-4.misc.iastate.edu:8080/game/leaderboard";
        Draft[] drafts = {new Draft_6455()};
        try
        {
            Log.d("Socket:", "Trying socket");
            socket = new WebSocketClient(new URI(url), (Draft) drafts[0])
            {
                @Override
                public void onOpen(ServerHandshake serverHandshake)
                {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String s)
                {
                    Log.d("", "run() returned: " + s);
                }

                @Override
                public void onClose(int i, String s, boolean b)
                {
                    Log.d("CLOSE", "onClose() returned: " + s);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e)
        {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        socket.connect();
    }
}
