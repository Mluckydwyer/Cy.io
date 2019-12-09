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
    public static final String NICKNAME = "username";
    public String bearer;
    public String token;
    public static String user;
    public String pass;
    boolean next = false;
    public static final String TAG = "Login-Page";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.LoginButton);
        username = findViewById(R.id.LoginUsernameTextBox);
        password = findViewById(R.id.LoginPasswordTextBox);

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setUser(username.getText().toString());
                setPass(password.getText().toString());
                JSONObject obj = new JSONObject();
                try
                {
                    obj.put("userName", getUser());
                    obj.put("password", getPass());
                }
                catch (JSONException e)
                {
                    System.out.println(e.getMessage());
                }
                String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
                bearer = " ";
                token = " ";
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
                        int q = response.toString().indexOf("accessToken");
                        int r = response.toString().indexOf("tokenType");
                        token = response.toString().substring(q + 14,r - 3);
                        bearer = response.toString().substring(r + 12, response.toString().length()- 2);
                        LoginSupport loginSupport = new LoginSupport();
                        loginSupport.credentials(token);
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

    public void setPass(String s)
    {
        pass = s;
    }

    public String getPass()
    {
        return pass;
    }

    public void setUser(String s)
    {
        user = s;
    }

    public String getUser()
    {
        return user;
    }
}
