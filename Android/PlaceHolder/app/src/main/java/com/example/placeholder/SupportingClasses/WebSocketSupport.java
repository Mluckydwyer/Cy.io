package com.example.placeholder.SupportingClasses;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.placeholder.Activities.LoginActivity;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.StompClient;

public class WebSocketSupport
{
    public static final String TAG = "lb-socket";
    public Disposable lifeCycle(StompClient mStompClient)
    {
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent ->
                {
                    switch (lifecycleEvent.getType())
                    {
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
        return dispLifecycle;
    }

    public void send(EditText userMessageTextBox, StompClient mStompClient)
    {
        if (userMessageTextBox.getText().toString().equals("")) return;
        Log.d(TAG, "Send Message: " + userMessageTextBox.getText().toString());
        String payload = JSONObject.quote("[" + LoginActivity.user + "] " + userMessageTextBox.getText().toString());
        Disposable disposable = mStompClient.send("/app/chat", payload).subscribe(new Action()
        {
            @Override
            public void run() throws Exception
            {
                userMessageTextBox.setText("");
            }
        });
    }



}
