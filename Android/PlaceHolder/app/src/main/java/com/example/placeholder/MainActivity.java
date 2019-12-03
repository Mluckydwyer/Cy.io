package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proceed = (Button) findViewById(R.id.MainToLoginButton);
        proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLoginPage();
            }
        });
    }
    public void openLoginPage()
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


    public void openGameList()
    {
        Intent i = new Intent(this, GameListActivity.class);
        startActivity(i);
    }
    //need to put on response for these
    private static final String TAG = "MainActivity";

//    protected  void respond()
//    {
//        // Tag used to cancel the request
//        String tag_json_obj ="json_obj_req";
//
//        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/restexample";
//        // "coms-309-nv-4.misc.iastate.edu:8080/restexample";
//        // or gamelist gives different options   //game has long id, String conten
//        // "api.androidive.info/volley/person_object.json";
//        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.GET, url,null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        String res = response.toString();
//                        CharSequence ch = res;
//                        Log.d(TAG, res);
//                        tex.setText(ch, EDITABLE);
//                    }
//                },new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                tex.setText("error", EDITABLE);
//                VolleyLog.d(TAG,"Error: "+ error.getMessage());
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }

//    protected void arrResponse()
//    {
//        String tag_json_arry ="json_array_req";
//        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
//        JsonArrayRequest req =new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>()
//                {
//                    @Override
//                    public void onResponse(JSONArray response)
//                    {
//                        Log.d(TAG, response.toString());
//                        String res = response.toString();
//                        CharSequence ch = res;
//                        Log.d(TAG, res);
//                        tex.setText(ch, EDITABLE);
//                    }
//                },new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                VolleyLog.d(TAG,"Error: "+ error.getMessage());
//            }
//        });
//        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
//    }


}
