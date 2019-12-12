package com.example.placeholder.SupportingClasses;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.placeholder.Activities.GameListActivity;
import com.example.placeholder.Models.Game;
import com.example.placeholder.R;
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
    private static final String TAG = "MainActivity";

//    public GameListSupport(LinkedGameList linkedGameList)
//    {
//        this.linkedGameList = linkedGameList;
//    }



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

    public void arrResponse() throws JSONException
    {
        String tag_json_arry = "json_array_req";
        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>()
                {
                    public ArrayList<Game> gameArrayList = new ArrayList<>();
                    public LinkedGameList linkedGameList1 = new LinkedGameList();
                    public void updateLinkedGameList(LinkedGameList updateList)
                    {
                        linkedGameList.head = updateList.head;
                        linkedGameList.head.next = updateList.head.next;
                        LinkedGameList.Node iter1 = linkedGameList.head;
                        LinkedGameList.Node iter2 = updateList.head;
                        while(iter2.next.game!= null)
                        {
                            iter1.next = iter2.next;
                            iter1 = iter1.next;
                            iter2 = iter2.next;
                        }

                    }

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

//                        linkedGameList1 = gameListSupport.createLinkedList(gameArrayList);
//                        updateLinkedGameList(linkedGameList1);
                        ArrayList<Game> g = setGameArrayList(gameArrayList);
                        Log.d("DDDD", g.get(0).getTitle());
                        g = getGameArrayList();
                        Log.d("DDDD", g.get(0).getTitle());
                        GameListActivity d = new GameListActivity();
                        d.displayGames(gameArrayList);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }



}
