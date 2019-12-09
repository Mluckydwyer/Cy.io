package com.example.placeholder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.placeholder.R;
import com.example.placeholder.SupportingClasses.WebSocketSupport;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatRoomActivity extends AppCompatActivity
{
    private String url = "ws://coms-309-nv-4.misc.iastate.edu:8080/chat/websocket";
    private StompClient mStompClient;
    private CompositeDisposable compositeDisposable;
    public static final String TAG = "lb-socket";
    Button sendBtn;
    EditText userMessageTextBox;
    LinearLayout chatHistoryParent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        sendBtn = findViewById(R.id.MessageSendButton);
        userMessageTextBox = findViewById(R.id.MessageTextBox);
        chatHistoryParent = findViewById(R.id.LinearLayoutChatRoom);


        compositeDisposable = new CompositeDisposable();
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, url);
        WebSocketSupport webSocketSupport = new WebSocketSupport();
        Disposable dispLifecycle = webSocketSupport.lifeCycle(mStompClient);
        mStompClient.connect();
        Disposable dispTopic = mStompClient.topic("/topic/chat").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage ->
        {
            Log.d(TAG, "MESSAGE: " + topicMessage.getPayload());
            String chatLog = new JSONObject(topicMessage.getPayload()).getString("text");

            TextView newChat = new TextView(getApplicationContext());
            newChat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newChat.setText(chatLog);
            chatHistoryParent.addView(newChat);
        }, throwable ->
        {
            Log.e("error", "Error on subscribe topic", throwable);
        });
        compositeDisposable.add(dispTopic);
        Log.d(TAG, dispTopic.toString());

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                webSocketSupport.send(userMessageTextBox, mStompClient);
            }
        });

    }
}
