package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class LeaderBoard extends AppCompatActivity
{

    private WebSocketClient socket;
    private String url;
    TextView title, place1, place2, place3, place4, place5;
    Button r, b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        r = (Button)findViewById(R.id.refresh);
        b = (Button)findViewById(R.id.gohome);
        title = (TextView)findViewById(R.id.lb);
        place1 = (TextView)findViewById(R.id.p1);
        place2 = (TextView)findViewById(R.id.p2);
        place3 = (TextView)findViewById(R.id.p3);
        place4 = (TextView)findViewById(R.id.p4);
        place5 = (TextView)findViewById(R.id.p5);
        url = "ws://coms-309-nv-4.misc.iastate.edu:8080/gamews";

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
                    Log.d("Socket", "run() returned: " + s);
                    Scanner strScan = new Scanner(s);
                    ArrayList<Player> players = new ArrayList<>();
                    int i = 0;
                    Log.d("SOCKETS1", strScan.next());
                    Log.d("SOCKETS2", strScan.next());
                    Log.d("SOCKETS3", strScan.next());
                    Log.d("SOCKETS4", strScan.next());
                    Log.d("SOCKETS5", strScan.next());
                    while (strScan.hasNext() == true && i < 5)
                    {
                        String name = "no name";
                        int score = 0;
                        String str = strScan.next();
                        if(str.equals("\"userName\""))
                        {
                            place1.setText("here");
                            strScan.next();
                            name = strScan.next();
                            name = name.substring(1, name.length() - 2);
                            place2.setText(name);
                            strScan.next(); //"userId"
                            strScan.next(); // :
                            strScan.next(); // null,
                            strScan.next(); //"score"
                            strScan.next(); // :
                            String scor = strScan.next();
                            scor = scor.substring(0, scor.length() - 1);
                            int p = 0;
                            for (int k = 0; k < scor.length(); k++)
                            {
                                p *= 10;
                                p += scor.charAt(k) - 48;
                            }
                            score = p;
                            place3.setText(scor);
                            place4.setText(p + "");
                            Log.d("SCORE", p + "");
                            if(!(name.equals("no name")) && (score != 0))
                            {
                                players.add(new Player(name, score));
                                i++;
                            }
                        }
                    }
                    for (int j = 0; j < 5; j++)
                    {
                        Player p = players.get(j);
                        if(j == 0)
                        {
                            place1.setText("1. " + p.getName() + ", " + p.getScoreString());
                        }
                        else if (j == 1)
                        {
                            place2.setText("2. " + p.getName() + ", " + p.getScoreString());
                        }
                        else if (j == 2)
                        {
                            place3.setText("3. " + p.getName() + ", " + p.getScoreString());
                        }
                        else if (j == 3)
                        {
                            place4.setText("4. " + p.getName() + ", " + p.getScoreString());
                        }
                        else if (j == 4)
                        {
                            place5.setText("5. " + p.getName() + ", " + p.getScoreString());
                        }
                    }
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
        r.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refresh();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    public void refresh()
    {
        Intent i = new Intent(this, LeaderBoard.class);
        startActivity(i);
    }

    public void goHome()
    {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }
}
