package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class login extends AppCompatActivity
{
        Button login;
        EditText username, password;
        TextView vt, vt2;
        public static final String NICKNAME = "username";
        public String bear;
        public String token;
        public String[] arr;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            login = (Button)findViewById(R.id.login);
            username = (EditText)findViewById(R.id.usernameEntry);
            password = (EditText)findViewById(R.id.passwordEntry);
            vt = (TextView) findViewById(R.id.tv1);
            vt2 = (TextView) findViewById(R.id.tv2);
            Log.d("vt has text", vt.getText().toString());
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
                    String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
                    bear = " ";
                    token = " ";
                    JsonObjectRequest jobj = signon(obj,vt,vt2);
                    AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
                    Log.d("AHhHhH", token);
                    //boolean b = credentials(arr.get(0), arr.get(1));
                    boolean b = credentials(bear, token);
                    Log.d("AHH", "" + b);
                    if (b == true)
                    {
                        openHomePage();
                    }
                    openHomePage();
                }
            });


        }

        public boolean credentials(String s1, String s2)
        {

            boolean b = true;
            if (s1.equals(s2))
            {
                b = false;
            }
//            ArrayList<String> arr = new ArrayList<>();
//            arr.add(s1);
//            arr.add(s2);
            final String a1 = s1;
            final String a2 = s2;
            //RequestQueue queue = Volley.newRequestQueue
            String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/user/me";
            StringRequest getRequest = new StringRequest(Request.Method.GET, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.d("ERROR", "error => " + error.toString());
                        }
                    }
                    )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + a2);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(getRequest, "bearer");
            return b;
        }



        protected JsonObjectRequest signon(JSONObject jo, final TextView vt, final TextView vt2)
        {
            String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/auth/login";

            Response.Listener<JSONObject> r = new Response.Listener<JSONObject>()
            {
                public String tok;
                public String bea;
                public String a[];

                public void updateArray(String[] a)
                {
                    arr = a;
                }
                public void updateToken()
                {
                    token = tok;
                }

                public void updateBear()
                {
                    bear = bea;
                }

                @Override
                public void onResponse(JSONObject response)
                {
                    JSONArray tokens = response.names();
                    vt.setTextSize(15);
                    try
                    {
                        vt.setText(response.getString(tokens.get(0).toString()));
                        tok = response.getString(tokens.get(0).toString());
                        vt2.setText(response.getString(tokens.get(1).toString()));
                        bea = response.getString(tokens.get(0).toString());
                        Log.d("AHhhhh", response.getString(tokens.get(0).toString()));
                        updateBear();
                        updateToken();
                        Log.d("AHhhhh", bear);
                        a = new String[] {bear, token};
                        Log.d("AHj", a[0]);
                        updateArray(a);
                        Log.d("AHk", arr[0]);
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
            };
            Response.ErrorListener e = new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.d("Login","Error: "+ error.getMessage());
                }
            };
            JsonObjectRequest jobj = new JsonObjectRequest(Request.Method.POST, URL, jo, r, e);
            return jobj;
        }

        public void openHomePage()
        {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

        public void openHomePage(String user)
        {
            Intent intent = new Intent(this, Home.class);
            //intent.putExtra(NICKNAME, user);
            startActivity(intent);
        }
}
