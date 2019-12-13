package com.example.placeholder.SupportingClasses;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.placeholder.Activities.GameListActivity;
import com.example.placeholder.Models.Game;
import com.example.placeholder.R;
import com.example.placeholder.SimpleListener;
import com.example.placeholder.SupportingClasses.LinkedGameList;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameListSupport extends AppCompatActivity
{
    public LinkedGameList linkedGameList = new LinkedGameList();
    public  ArrayList<Game> gameArrayList;
    private static final String TAG = "GameListSupport";

    public LinkedGameList createLinkedList(ArrayList<Game> gameArrayList)
    {
        LinkedGameList lgl = new LinkedGameList();
        for (int i = 0; i < gameArrayList.size(); i++)
        {
            lgl.AddToList(gameArrayList.get(i));
        }
        return lgl;
    }

    public LinkedGameList getList()
    {
        return linkedGameList;
    }

    public ArrayList<Game> lgltoarr(LinkedGameList linkedGameList)
    {
        LinkedGameList.Node n = linkedGameList.head;
        ArrayList<Game> gameArrayList = new ArrayList<>();
        while (n.next.game != null)
        {
            n = n.next;
            gameArrayList.add(n.game);
        }
        return gameArrayList;
    }

    public ArrayList<Game> setGameArrayList(ArrayList<Game> gameArrayList)
    {
        this.gameArrayList = gameArrayList;
        return this.gameArrayList;
    }

    public ArrayList<Game> getGameArrayList()
    {
        return gameArrayList;
    }

    private static GameListSupport instance = null;
    private static final String URL = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
    public RequestQueue requestQueue;
    private GameListSupport(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized GameListSupport getInstance(Context context)
    {
        if (null == instance)
        {
            instance = new GameListSupport(context);
        }
        return instance;
    }

    public static synchronized GameListSupport getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(GameListSupport.class.getSimpleName() + "is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void JSONRequest(final SimpleListener<ArrayList<Game>> listener)
    {
        String url = URL;
        ArrayList<Game> gameArrayList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                for (int i = 0; i < response.length(); i++)
                {
                    JSONArray responseNames;
                    try
                    {
                        JSONObject responseJSONObject = response.getJSONObject(i);
                        responseNames = responseJSONObject.names();

                        Game game = new Game();
                        game.setTitle(responseJSONObject.getString(responseNames.get(0).toString()));
                        game.setBlurb(responseJSONObject.getString(responseNames.get(1).toString()));
                        game.setAbout(responseJSONObject.getString(responseNames.get(2).toString()));
                        game.setGameID(responseJSONObject.getString(responseNames.get(3).toString()));
                        game.setCreatorID(responseJSONObject.getString(responseNames.get(4).toString()));
                        gameArrayList.add(game);
                    }
                    catch (JSONException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                listener.getResult(gameArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }
}
