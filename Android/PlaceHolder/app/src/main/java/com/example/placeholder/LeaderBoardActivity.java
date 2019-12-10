package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class LeaderBoardActivity extends AppCompatActivity
{

    private final String url = "ws://coms-309-nv-4.misc.iastate.edu:8080/leaderboard/websocket";
    TextView title;
    Button returnToHomeBtn;
    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    public static final String TAG = "lb-socket";
    public ArrayList<Player> players;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        returnToHomeBtn = findViewById(R.id.LeaderBoardReturnHomeButton);
        title = findViewById(R.id.LeaderBoardTitleTextView);
        players = new ArrayList<>();
        ArrayList<TextView> leaders = new ArrayList<>();
        leaders.add(findViewById(R.id.Player1TextView));
        leaders.add(findViewById(R.id.Player2TextView));
        leaders.add(findViewById(R.id.Player3TextView));
        leaders.add(findViewById(R.id.Player4TextView));
        leaders.add(findViewById(R.id.Player5TextView));
        Log.d(TAG, "On Create");
        compositeDisposable = new CompositeDisposable();
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, url);
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d(TAG, "Stomp connection opened");
                            break;
                        case ERROR:
                            Log.e(TAG, "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            Log.d(TAG, "Stomp connection closed");
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.d(TAG, "Stomp failed server heartbeat");
                            break;
                    }
                });
        mStompClient.connect();
        Disposable dispTopic = mStompClient.topic("/topic/leaderboard").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage ->
                {
                    Log.d(TAG, "MESSAGE: " + topicMessage.getPayload());
                    players = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(topicMessage.getPayload());
                    for (int i = 0; i < leaders.size(); i++)
                    {
                        JSONObject leader = (JSONObject) jsonArray.get(i);
                        String leaderText = "1. " + leader.getString("name") + " - " + leader.getString("score");
                        players.add(new Player(leader.getString("name"), leader.getInt("score")));
                        leaders.get(i).setText(leaderText);
                    }
                }, throwable ->
                {
                    Log.e("error", "Error on subscribe topic", throwable);
                });
        compositeDisposable.add(dispTopic);
        Log.d(TAG,dispTopic.toString());

        returnToHomeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    public String seePlayers()
    {
        String s = "";
        for(int i = 0; i < players.size(); i++)
        {
            s += players.get(i).getName() + ", " + players.get(i).getScoreString();
        }
        return s;
    }

    public String getPlayerName(int i)
    {
        return players.get(i).getName();
    }

    public int getPlayerScore(int i)
    {
        return players.get(i).getScore();
    }


    public void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}
