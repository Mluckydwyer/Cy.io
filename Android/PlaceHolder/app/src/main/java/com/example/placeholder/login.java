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
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class login extends AppCompatActivity
{
        Button logi;
        EditText username, password;
        TextView vt, vt2;
        public static final String NICKNAME = "username";
        public String bear;
        public String token;
        public String[] arr;
        public String user;
        public String pass;
        boolean next = false;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            logi = (Button)findViewById(R.id.login);
            username = (EditText)findViewById(R.id.usernameEntry);
            password = (EditText)findViewById(R.id.passwordEntry);
            vt = (TextView) findViewById(R.id.tv1);
            vt2 = (TextView) findViewById(R.id.tv2);
            Log.d("vt has text", vt.getText().toString());

            logi.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    setUser(username.getText().toString());
                    setPass(password.getText().toString());
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
                    makeJsonObjectRequest(URL, obj, new VolleyResponseListener()
                    {
                        public boolean b = false;
                        @Override
                        public void onError(String message)
                        {
                            Log.d("ERROR", message);
                            Log.d("BBB", b + "");
                        }

                        @Override
                        public void onResponse(Object response)
                        {
                            Log.d("BB", response.toString());
                            Scanner scan = new Scanner(response.toString());
                            int q = response.toString().indexOf("accessToken");
                            int r = response.toString().indexOf("tokenType");
                            Log.d("BBBB", q + "");
                            Log.d("BBBB", r + "");
                            token = response.toString().substring(q + 14,r - 3);
                            bear = response.toString().substring(r + 12, response.toString().length()- 2);
                            Log.d("BBBBB", token);
                            Log.d("BBBBB", bear);
                            b = credentials(bear, token);
                            Log.d("BBBBBB", b + "");
                            openHomePage();

                        }
                    });
//                    Log.d("BBb", token);
//                    Log.d("BBb", bear);
//                    JsonObjectRequest jobj = signon(obj,vt,vt2);
//                    AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
//                    //boolean b = credentials(arr.get(0), arr.get(1));
//                    boolean b = credentials(bear, token);
////                    Log.d("AHH", "" + b);
//                    if (b == true)
//                    {
//                        openHomePage();
//                    }
//                    openHomePage();
                }
            });


        }

        public static void makeJsonObjectRequest(String url, JSONObject jo, final VolleyResponseListener listener)
        {
            Log.d("B", "here");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (url, jo, new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            listener.onResponse(response);
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            listener.onError(error.toString());
                        }
                    })
            {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
                {
                    try
                    {
                        String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                        return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        return Response.error(new ParseError(e));
                    }
                    catch (JSONException je)
                    {
                        return Response.error(new ParseError(je));
                    }
                }
            };
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
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



        public JsonObjectRequest signon(JSONObject jo, final TextView vt, final TextView vt2)
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
//                        Log.d("AHhhhh", response.getString(tokens.get(0).toString()));
                        updateBear();
                        updateToken();
//                        Log.d("AHhhhh", bear);
                        a = new String[] {bear, token};
//                        Log.d("AHj", a[0]);
                        updateArray(a);
//                        Log.d("AHk", arr[0]);
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
            String tag_json_obj ="json_obj_req";
            JsonObjectRequest jobj = new JsonObjectRequest(Request.Method.POST, URL, jo, r, e);
            AppController.getInstance().addToRequestQueue(jobj, tag_json_obj);
            return jobj;
        }

        public void openHomePage()
        {
            next = true;
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

        public boolean getNext()
        {
            return next;
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
//
//        public void openHomePage(String user)
//        {
//            Intent intent = new Intent(this, Home.class);
//            //intent.putExtra(NICKNAME, user);
//            startActivity(intent);
//        }
}
