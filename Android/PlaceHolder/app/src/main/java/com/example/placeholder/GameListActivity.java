package com.example.placeholder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.placeholder.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //TextView text = new TextView(this);
    public ArrayList<Game> gameList;
    JSONArray arr = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        Button ba = (Button) findViewById(R.id.back);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        //TextView vt = (TextView) findViewById(R.id.scrolledText);
        try {

            arrResponse();


        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    public void goHome() {
        Intent it = new Intent(this, Home.class);
        startActivity(it);
    }

    protected void arrResponse() throws JSONException {
        String tag_json_arry = "json_array_req";
        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
        // ArrayList<String> biglist = new ArrayList<String>();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    public ArrayList<Game> midlist = new ArrayList<>();

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONArray st;
                            try {
                                JSONObject jj = response.getJSONObject(i);
                                st = jj.names();

                                Game game = new Game();
                                game.setTitle(st.get(0).toString());
                                game.setBlurb(st.get(1).toString());
                                game.setAbout(st.get(2).toString());
                                game.setGameID(st.get(3).toString());
                                game.setCreatorID(st.get(4).toString());
                                midlist.add(game);

                                /*
                                0=Title
                                1=blurb
                                2=about
                                3=gameID
                                4=Creator ID
                                5=thumbnail
                                 */
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        displayGames(midlist);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }


    private void displayGames(List<Game> gameList) {
        for (Game game : gameList) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.llMain);
            TextView Title = new TextView(getApplicationContext());
            Title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String title = game.getTitle();
            Title.setText(title + "\n");
            Title.setTextSize(20);
            ll.addView(Title);

            TextView blurb = new TextView(getApplicationContext());
            blurb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String bl = game.getBlurb();
            blurb.setText(bl);
            blurb.setTextSize(15);
            ll.addView(blurb);

            TextView about = new TextView(getApplicationContext());
            about.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String ab = game.getAbout();
            about.setText("Description:" + ab);
            about.setTextSize(15);
            ll.addView(about);

            TextView gameID = new TextView(getApplicationContext());
            gameID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String ga = game.getGameID();
            gameID.setText("Game ID:" + ga);
            gameID.setTextSize(15);
            ll.addView(gameID);

            TextView creatorID = new TextView(getApplicationContext());
            creatorID.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String cr = game.getCreatorID();
            creatorID.setText("Created by:" + cr + "\n");
            creatorID.setTextSize(15);
            ll.addView(creatorID);
        }
    }

    public List<Game> parseGameList() {
        if (gameList == null) {
            return null;
        }
        ArrayList<Game> listofgames = new ArrayList<Game>();
//        for (int parser = 0; parser < gametitles.size(); parser++)
//        {
//            listofgames.add(gameList.get(parser));
//        }
        return listofgames;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void addToList(Game g) {

    }

}
