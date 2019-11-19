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
import org.json.JSONObject;
import org.reactivestreams.Subscriber;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscribers.BlockingBaseSubscriber;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

public class LeaderBoard extends AppCompatActivity {

    private WebSocketClient socket;
    private final String url = "ws://coms-309-nv-4.misc.iastate.edu:8080/leaderboard/websocket";
    TextView title, place1, place2, place3, place4, place5;
    Button r, b;
    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    public static final String TAG = "lb-socket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        r = (Button) findViewById(R.id.refresh);
        b = (Button) findViewById(R.id.gohome);
        title = (TextView) findViewById(R.id.lb);

        // Get leaderboard textViews
        ArrayList<TextView> leaders = new ArrayList<>();
        leaders.add((TextView) findViewById(R.id.p1));
        leaders.add((TextView) findViewById(R.id.p2));
        leaders.add((TextView) findViewById(R.id.p3));
        leaders.add((TextView) findViewById(R.id.p4));
        leaders.add((TextView) findViewById(R.id.p5));

        Draft[] drafts = {new Draft_6455()};
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
                    JSONArray jsonArray = new JSONArray(topicMessage.getPayload());
                    for (int i = 0; i < leaders.size(); i++) {
                        JSONObject leader = (JSONObject) jsonArray.get(i);
                        String leaderText = "1. " + leader.getString("name") + " - " + leader.getString("score");
                       leaders.get(i).setText(leaderText);
                    }
                }, throwable ->
                {
                    Log.e("error", "Error on subscribe topic", throwable);
                });
        compositeDisposable.add(dispTopic);
        Log.d(TAG,dispTopic.toString());

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void refresh() {
        Intent i = new Intent(this, LeaderBoard.class);
        startActivity(i);
    }

    public void goHome() {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }


}
