package com.example.placeholder;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class LoginTest
{
    @Mock
    login l;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void loginTest()
//    {
//        TextView vt = null;
//        JSONObject jo = null;
//        when(l.signon(jo,vt)).thenReturn(new JsonObjectRequest(Request.Method.POST, URL, jo, new Response.Listener<JSONObject>()
//        {
//            @Override
//            public void onResponse(JSONObject response)
//            {
//                JSONArray tokens = response.names();
//                vt.setTextSize(15);
//                try
//                {
//                    vt.setText(response.getString(tokens.get(0).toString()) + response.getString(tokens.get(1).toString()));
//                }
//                catch (JSONException e)
//                {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                VolleyLog.d("Login","Error: "+ error.getMessage());
//            }
//        });
//    }
}
