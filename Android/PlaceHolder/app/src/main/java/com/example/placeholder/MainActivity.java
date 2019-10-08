package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.widget.TextView.BufferType.EDITABLE;

public class MainActivity extends AppCompatActivity
{
    Button gamelist, leaderboard;
    EditText tex;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamelist = (Button) findViewById(R.id.gamelist);
        leaderboard = (Button) findViewById(R.id.leaderboard);
        tex = (EditText) findViewById(R.id.editText);


        gamelist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                respond();

            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                arrResponse();
            }
        });



    }
    //need to put on response for these
    private static final String TAG = "MainActivity";

    protected  void respond()
    {
        // Tag used to cancel the request
        String tag_json_obj ="json_obj_req";

        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/restexample";
        // "coms-309-nv-4.misc.iastate.edu:8080/restexample";
        // or gamelist gives different options   //game has long id, String conten
        // "api.androidive.info/volley/person_object.json";
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String res = response.toString();
                        CharSequence ch = res;
                        Log.d(TAG, res);
                        tex.setText(ch, EDITABLE);
                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                tex.setText("error", EDITABLE);
                VolleyLog.d(TAG,"Error: "+ error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    protected void arrResponse()
    {
        String tag_json_arry ="json_array_req";
        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
        JsonArrayRequest req =new JsonArrayRequest(url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString());
                        String res = response.toString();
                        CharSequence ch = res;
                        Log.d(TAG, res);
                        tex.setText(ch, EDITABLE);
                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG,"Error: "+ error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }


}