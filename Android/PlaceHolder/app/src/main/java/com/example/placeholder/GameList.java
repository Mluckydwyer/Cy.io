package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;
import com.example.placeholder.Game;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.widget.TextView.BufferType.EDITABLE;

public class GameList extends AppCompatActivity
{
    JSONArray arr = new JSONArray();
    //TextView text = new TextView(this);
    public ArrayList<Game> gameList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        Button ba = (Button) findViewById(R.id.back);
        ba.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goHome();
            }
        });
        //TextView vt = (TextView) findViewById(R.id.scrolledText);
        try
        {

            arrResponse();


        }
        catch (JSONException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void goHome()
    {
        Intent it = new Intent(this, Home.class);
        startActivity(it);
    }
    private static final String TAG = "MainActivity";

    public LinkedGameList lgll;
    protected void arrResponse() throws JSONException
    {
        String tag_json_arry ="json_array_req";
        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
        ArrayList<String> biglist = new ArrayList<String>();
        JsonArrayRequest req =new JsonArrayRequest(url,
                new Response.Listener<JSONArray>()
                {
                    public ArrayList<Game> midlist = new ArrayList<>();
                    public LinkedGameList lgl = lgll;

                    public void updateLinkedGameList(LinkedGameList lgl)
                    {
                        lgll = lgl;
                        //copy method
                    }

                    @Override
                    public void onResponse(JSONArray response)
                    {
                        ArrayList<String> list = new ArrayList<String>();
                        for(int i = 0; i < response.length(); i++)
                        {
                            JSONArray st;
                            try
                            {
                                JSONObject jj = response.getJSONObject(i);
                                st = jj.names();
                                LinearLayout ll = (LinearLayout) findViewById(R.id.llMain);

                                TextView Title = new TextView(getApplicationContext());
                                Title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                String ti = jj.getString(st.get(0).toString());
                                Title.setText(ti + "\n");
                                Title.setTextSize(20);
                                ll.addView(Title);
                                list.add(ti);

                                TextView blurb = new TextView(getApplicationContext());
                                blurb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                String bl = jj.getString(st.get(1).toString());
                                blurb.setText(bl);
                                blurb.setTextSize(15);
                                ll.addView(blurb);
                                list.add(bl);

                                TextView about = new TextView(getApplicationContext());
                                about.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                String ab = jj.getString(st.get(2).toString());
                                about.setText("Description:" + ab);
                                about.setTextSize(15);
                                ll.addView(about);
                                list.add(ab);

                                TextView gameID = new TextView(getApplicationContext());
                                gameID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                String ga = jj.getString(st.get(3).toString());
                                gameID.setText("Game ID:" + ga);
                                gameID.setTextSize(15);
                                ll.addView(gameID);
                                list.add(ga);

                                TextView creatorID = new TextView(getApplicationContext());
                                creatorID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                                String cr = jj.getString(st.get(4).toString());
                                creatorID.setText("Created by:"+ bl + "\n");
                                creatorID.setTextSize(15);
                                ll.addView(creatorID);
                                list.add(cr);

                                /*
                                0=Title
                                1=blurb
                                2=about
                                3=gameID
                                4=Creator ID
                                5=thumbnail
                                 */
                            }
                            catch (JSONException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        for(int j = 0; j < list.size(); j += 5)
                        {
                            Game gg = new Game(list.get(j), list.get(j + 1), list.get(j + 2), list.get(j + 3), list.get(j + 4));
                            midlist.add(gg);
                        }
                        lgl = createLinkedList(midlist);
                        updateLinkedGameList(lgl);
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

    public LinkedGameList createLinkedList(ArrayList<Game> midlist)
    {
        LinkedGameList lgl = new LinkedGameList();
        for (int i = 0; i < midlist.size(); i++)
        {
            lgl.AddToList(midlist.get(i));
        }
        return lgl;
    }

    public LinkedGameList getList()
    {
        return lgll;
    }

}
