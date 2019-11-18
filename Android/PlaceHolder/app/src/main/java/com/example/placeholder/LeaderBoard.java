package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

import java.net.URI;
import java.net.URISyntaxException;

public class LeaderBoard extends AppCompatActivity
{

    private WebSocketClient socket;
    private String url;
    TextView title, place1, place2, place3, place4, place5;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        title = (TextView)findViewById(R.id.lb);
        place1 = (TextView)findViewById(R.id.p1);
        place2 = (TextView)findViewById(R.id.p2);
        place3 = (TextView)findViewById(R.id.p3);
        place4 = (TextView)findViewById(R.id.p4);
        place5 = (TextView)findViewById(R.id.p5);
        url = "ws://coms-309-nv-4.misc.iastate.edu:8081/gamews";
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

                    String str = place1.getText().toString();
                    place1.setText("hello world");
                    Log.d("first", "run() returned: " + str);
                    str = place1.getText().toString();
                    Log.d("second", "run() returned: " + str);
                    place1.setText(str + " Server:" + s);
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
