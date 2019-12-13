package com.example.placeholder.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.placeholder.Models.Game;
import com.example.placeholder.SimpleListener;
import com.example.placeholder.SupportingClasses.GameListSupport;
import com.example.placeholder.SupportingClasses.LinkedGameList;
import com.example.placeholder.R;
import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity
{

    public LinkedGameList linkedGameList = new LinkedGameList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GameListSupport.getInstance(this);
        setContentView(R.layout.activity_game_list);
        Button backBtn = findViewById(R.id.BackButtonGameList);
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goHome();
            }
        });
        GameListSupport.getInstance().JSONRequest(new SimpleListener<ArrayList<Game>>()
        {
               @Override
               public void getResult(ArrayList<Game> object)
               {
                    displayGames(object);
               }
        });
    }
    private void goHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void displayGames(List<Game> gameList) {
        for (Game game : gameList) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutGameList);
            TextView titleTextView = new TextView(getApplicationContext());
            titleTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String title = game.getTitle();
            titleTextView.setText(title + "\n");
            titleTextView.setTextSize(20);
            titleTextView.setTextColor(Color.BLACK);
            linearLayout.addView(titleTextView);

            TextView blurbTextView = new TextView(getApplicationContext());
            blurbTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String blurb = game.getBlurb();
            blurbTextView.setText(blurb);
            blurbTextView.setTextSize(15);
            blurbTextView.setTextColor(Color.BLACK);
            linearLayout.addView(blurbTextView);

            TextView aboutTextView = new TextView(getApplicationContext());
            aboutTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String about = game.getAbout();
            aboutTextView.setText("Description:" + about);
            aboutTextView.setTextSize(15);
            aboutTextView.setTextColor(Color.BLACK);
            linearLayout.addView(aboutTextView);

            TextView gameIDTextView = new TextView(getApplicationContext());
            gameIDTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String gameID = game.getGameID();
            gameIDTextView.setText("Game ID:" + gameID);
            gameIDTextView.setTextSize(15);
            gameIDTextView.setTextColor(Color.BLACK);
            linearLayout.addView(gameIDTextView);

            TextView creatorIDTextView = new TextView(getApplicationContext());
            creatorIDTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String creatorID = game.getCreatorID();
            creatorIDTextView.setText("Created by:" + creatorID + "\n");
            creatorIDTextView.setTextSize(15);
            creatorIDTextView.setTextColor(Color.BLACK);
            linearLayout.addView(creatorIDTextView);
        }
    }
}
