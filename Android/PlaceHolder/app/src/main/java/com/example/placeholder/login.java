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


public class login extends AppCompatActivity
{
    Button login;
    EditText username, password;
    TextView vt, vt2;
    public static final String NICKNAME = "username";
    public String bear;
    public String token;
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
                JsonObjectRequest jobj = signon(obj,vt,vt2);
                AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
                RequestQueue q = AppController.getInstance().getRequestQueue();
                q.getCache().get("bearer");




                if(vt.getText() != "")
                {
                    vt.setText("");
                    vt2.setText("");
                    openHomePage(user);
                }
                vt.setText("");
                vt2.setText("");
            }
        });


    }

    public static ArrayList<String> credentials(String s1, String s2)
    {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(s1);
        arr.add(s2);
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
                params.put(a1, a2);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getRequest, "bearer");
        return arr;
    }

    public static JsonObjectRequest signon(JSONObject jo, TextView vt, TextView vt2)
    {
        String URL = "http://coms-309-nv-4.misc.iastate.edu:8081/auth/login";
        ArrayList<String> arr = new ArrayList<>();
        final TextView t = vt;
        final TextView t2 = vt2;
        Response.Listener<JSONObject> r = new Response.Listener<JSONObject>()
        {
            public ArrayList<String> arr1;
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray tokens = response.names();
                t.setTextSize(15);
                try
                {
                    arr1 = credentials(tokens.get(1).toString(), tokens.get(0).toString());
                    t.setText(response.getString(tokens.get(0).toString()));
                    t2.setText(response.getString(tokens.get(1).toString()));
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




                  //new Response.Listener<JSONObject>()
//        {
//            public ArrayList<String> arr1;
//
//            public ArrayList<String> sync()
//            {
//                for (int i = 0; i < arr1.size(); i++)
//                {
//                    arr.get(i) = arr1.get(i);
//                }
//                return arr;
//            }
//
//            public ArrayList<String> getArr()
//            {
//                return arr1;
//            }
//
//            @Override
//            public void onResponse(JSONObject response)
//            {
//                JSONArray tokens = response.names();
//                vt.setTextSize(15);
//                try
//                {
//                    arr1 = credentials(tokens.get(1).toString(), tokens.get(0).toString());
//                    vt.setText(response.getString(tokens.get(0).toString()) + response.getString(tokens.get(1).toString()));
//                    //credentials(tokens.get(0));
//                   // new JsonObjectRequest(Request.Method.GET, "http://coms-309-nv-4.misc.iastate.edu:8080/user/me", )
//                    //get(0) returns randomstring of chars
//                    //get(1) returns bearer
//                }
//                catch (JSONException e)
//                {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }



        return jobj;
    }

    public void openHomePage(String user)
    {
        Intent intent = new Intent(this, Home.class);
        //intent.putExtra(NICKNAME, user);
        startActivity(intent);
    }
}
