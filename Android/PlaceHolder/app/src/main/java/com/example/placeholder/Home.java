package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity
{
    Button g, c, l;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button g = (Button)findViewById(R.id.gamelist);
        Button c = (Button)findViewById(R.id.chat);
        Button l = (Button)findViewById(R.id.leaderboard);

        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openGameList();
            }
        });

        c.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openChat();
            }
        });

        l.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLeaderBoard();
            }
        });
    }

    public void openGameList()
    {
        Intent i = new Intent(this, GameListActivity.class);
        startActivity(i);
    }

    public void openChat()
    {
        Intent i = new Intent(this, ChatRoom.class);
        startActivity(i);
    }

    public void openLeaderBoard()
    {
        Intent i = new Intent(this, GameListActivity.class);
        startActivity(i);
    }
}
