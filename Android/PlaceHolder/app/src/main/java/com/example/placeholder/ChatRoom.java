package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

//import ua.naiksoftware.stomp.Stomp;
//import ua.naiksoftware.stomp.StompClient;

public class ChatRoom extends AppCompatActivity
{
    private WebSocketClient socket;
    private String name;
    private String url = "";
    private String url2 = "";
    Button send;
    EditText et;
//    private StompClient mStompClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        send = (Button) findViewById(R.id.sendbtn);
        et = (EditText) findViewById(R.id.messagetxt);
        url = "ws://coms-309-nv-4.misc.iastate.edu:8081/chat";
        url2 = "http://coms-309-nv-4.misc.iastate.edu:8080/topic/chat";
//        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
//        mStompClient.connect();
//        try
//        {
//            socket = new WebSocketClient(new URI(url))
//            {
//                LinearLayout ll = (LinearLayout) findViewById(R.id.llcr);
//                @Override
//                public void onOpen(ServerHandshake serverHandshake)
//                {
//                    Log.d("OPEN", "run() returned: " + "is connecting");
//                }
//
//                @Override
//                public void onMessage(String s)
//                {
//                    Log.d("", "run() returned: " + s);
//                    TextView t = new TextView(getApplicationContext());
//                    t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    t.setTextSize(15);
//                    t.setText(s);
//                    ll.addView(t);
//                }
//
//                @Override
//                public void onClose(int i, String s, boolean b)
//                {
//                    Log.d("CLOSE", "onClose() returned: " + s);
//                }
//
//                @Override
//                public void onError(Exception e)
//                {
//                    Log.d("Exception:", e.toString());
//                }
//            };
//        }
//        catch (URISyntaxException e)
//        {
//            e.printStackTrace();
//        }
//        socket.connect();

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LinearLayout ll = (LinearLayout) findViewById(R.id.llcr);
                TextView t = new TextView(getApplicationContext());
                t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                t.setTextSize(15);
                t.setText(et.getText().toString());
                ll.addView(t);
//                socket.send(et.getText().toString());
            }
        });
    }
}
