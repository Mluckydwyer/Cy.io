package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.placeholder.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


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
                    obj.put("userName", user);
                    obj.put("password", pass);
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
                        Log.d(TAG, q + "");
                        Log.d(TAG, r + "");
                        token = response.toString().substring(q + 14,r - 3);
                        bearer = response.toString().substring(r + 12, response.toString().length()- 2);
                        Log.d(TAG, token);
                        Log.d(TAG, bearer);
                        credentials(token);
                        openHomePage();
                    }
                });
            }
        });

    }

    public static void makeJsonObjectRequest(String url, JSONObject jsonObject, final VolleyResponseListener listener)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, jsonObject, new Response.Listener<JSONObject>()
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

    public void credentials(String authorizationToken)
    {
        final String authToken = authorizationToken;
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
                params.put("Authorization", "Bearer " + authToken);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getRequest, "bearer");
    }

    public void openHomePage()
    {
        next = true;
        Intent intent = new Intent(this, HomeActivity.class);
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
}
