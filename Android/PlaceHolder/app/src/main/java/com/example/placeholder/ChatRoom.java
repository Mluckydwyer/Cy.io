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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

//import ua.naiksoftware.stomp.Stomp;
//import ua.naiksoftware.stomp.StompClient;

public class ChatRoom extends AppCompatActivity
{
    private WebSocketClient socket;
    private String name;
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
        sendBtn = (Button) findViewById(R.id.sendbtn);
        userMessageTextBox = (EditText) findViewById(R.id.messagetxt);
        chatHistoryParent = (LinearLayout) findViewById(R.id.llcr);


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

        Disposable dispTopic = mStompClient.topic("/topic/chat").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(topicMessage ->
        {
            Log.d(TAG, "MESSAGE: " + topicMessage.getPayload());
            String chatLog = userMessageTextBox.getText() + "\n" + topicMessage.getPayload();
            userMessageTextBox.setText(chatLog);
        }, throwable ->
        {
            Log.e("error", "Error on subscribe topic", throwable);
        });
        compositeDisposable.add(dispTopic);
        Log.d(TAG,dispTopic.toString());



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

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView newChat = new TextView(getApplicationContext());
                newChat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //newChat.setTextSize(15);
                newChat.setText(userMessageTextBox.getText().toString());
                chatHistoryParent.addView(newChat);
                mStompClient.send("/app/chat", userMessageTextBox.getText().toString()); //.subscribe();
            }
        });
    }
}
