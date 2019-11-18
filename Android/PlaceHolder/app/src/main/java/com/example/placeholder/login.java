package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity
{
    Button login;
    EditText username, password;
    TextView vt;
    public static final String NICKNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.usernameEntry);
        password = (EditText)findViewById(R.id.passwordEntry);
        vt = (TextView) findViewById(R.id.loginPrompt);
        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String tag_json_obj ="json_obj_req";

                JSONObject obj = new JSONObject();
                try
                {
                    obj.put("userName", user);
                    obj.put("password", pass);
                }
                catch (JSONException e)
                {
                    System.out.println(e.getMessage());
                }
                String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/auth/login";
                JsonObjectRequest jobj = signon(obj,vt);
                AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
                openHomePage(user);
            }
        });


    }
    public static JsonObjectRequest credentials(JSONObject jo)
    {
        String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/user/me";
        return new JsonObjectRequest(Request.Method.GET, URL, jo, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d("Login","Error: "+ error.getMessage());
            }
        });

    }
    public static JsonObjectRequest signon(JSONObject jo, final TextView vt)
    {
        String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/auth/login";
        return new JsonObjectRequest(Request.Method.POST, URL, jo, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray tokens = response.names();
                vt.setTextSize(15);
                try
                {
                    vt.setText(response.getString(tokens.get(0).toString()) + response.getString(tokens.get(1).toString()));
                    //credentials(tokens.get(0));
                   // new JsonObjectRequest(Request.Method.GET, "http://coms-309-nv-4.misc.iastate.edu:8080/user/me", )
                    //get(0) returns randomstring of chars
                    //get(1) returns bearer
                }
                catch (JSONException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d("Login","Error: "+ error.getMessage());
            }
        });
    }
    public void openHomePage(String user)
    {
        Intent intent = new Intent(this, Home.class);
        //intent.putExtra(NICKNAME, user);
        startActivity(intent);
    }
}
