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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.usernameEntry);
        password = (EditText)findViewById(R.id.passwordEntry);
        vt = (TextView) findViewById(R.id.tv1);
        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openHomePage();
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
                String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
                JsonObjectRequest jobj = signon(obj,vt);
                AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
            }
        });


    }

    public static JsonObjectRequest signon(JSONObject jo, final TextView vt)
    {
        String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
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
    public void openHomePage()
    {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
