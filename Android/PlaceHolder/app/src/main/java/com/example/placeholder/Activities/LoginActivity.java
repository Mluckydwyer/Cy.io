package com.example.placeholder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.placeholder.R;
import com.example.placeholder.VolleyResponseListener;
import com.example.placeholder.SupportingClasses.LoginSupport;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.placeholder.SupportingClasses.LoginSupport.makeJsonObjectRequest;


public class LoginActivity extends AppCompatActivity
{
    Button loginBtn;
    EditText username, password;
    public String bearer = "";
    public String token = "";
    public static String user;
    public static final String TAG = "Login-Page";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.LoginButton);
        username = findViewById(R.id.LoginUsernameTextBox);
        password = findViewById(R.id.LoginPasswordTextBox);

        LoginSupport.getInstance(this);
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user = username.getText().toString();
                JSONObject obj = new JSONObject();
                try
                {
                    obj.put("userName", username.getText().toString());
                    obj.put("password", password.getText().toString());
                }
                catch (JSONException e)
                {
                    System.out.println(e.getMessage());
                }
                String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
                makeJsonObjectRequest(URL, obj, new VolleyResponseListener()
                {
                    @Override
                    public void onError(String message)
                    {
                        Log.d("ERROR", message);
                    }

                    @Override
                    public void onResponse(Object response)
                    {
                        Log.d(TAG, response.toString());
                        int accessToken = response.toString().indexOf("accessToken");
                        int tokenType = response.toString().indexOf("tokenType");
                        token = response.toString().substring(accessToken + 14,tokenType - 3);
                        bearer = response.toString().substring(tokenType + 12, response.toString().length()- 2);
                        LoginSupport.getInstance().credentials(token);
                        openHomePage();
                    }
                });
            }
        });
    }

    public void openHomePage()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
