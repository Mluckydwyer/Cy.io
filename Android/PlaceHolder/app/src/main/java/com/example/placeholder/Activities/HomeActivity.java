package com.example.placeholder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.placeholder.R;

public class HomeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button gameBtn = findViewById(R.id.ButtonForGameList);
        Button chatRoomBtn = findViewById(R.id.ButtonForChatRoom);
        Button leaderboardBtn = findViewById(R.id.ButtonForLeaderboard);

        gameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openGameList();
            }
        });

        chatRoomBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openChat();
            }
        });

        leaderboardBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openLeaderBoard();
            }
        });
    }

    public void openGameList()
    {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }

    public void openChat()
    {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        startActivity(intent);
    }

    public void openLeaderBoard()
    {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }
}
