package com.example.placeholder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class LoginTest
{
    @Mock
    LoginActivity l;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void userTest()
    {
        l.user = "chuck";
        assertEquals("chuck", l.user);
    }

    @Test
    public void null_UserTest()
    {
        l.setUser(null);
        assertEquals(null, l.getUser());
    }

    @Test
    public void falseBoolCredentialTest()
    {
        String s1 = "a";
        String s2 = "a";
        boolean b = false;
        when(l.credentials(s1,s2)).thenReturn(false);
        b = l.credentials(s1,s2);
        assertEquals(b, false);
    }

    @Test
    public void trueBoolCredentialTest()
    {
        String s1 = "a";
        String s2 = "returnToHomeBtn";
        boolean b = false;
        when(l.credentials(s1,s2)).thenReturn(true);
        b = l.credentials(s1,s2);
        assertEquals(b, true);
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
