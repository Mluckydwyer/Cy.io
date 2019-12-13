package com.example.placeholder.SupportingClasses;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.placeholder.VolleyResponseListener;
import com.example.placeholder.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginSupport
{
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
}
