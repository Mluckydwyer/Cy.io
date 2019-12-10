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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        Button backBtn = findViewById(R.id.BackButtonGameList);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        try
        {
            arrResponse();
        } catch (JSONException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void goHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public LinkedGameList linkedGameList;
    protected void arrResponse() throws JSONException {
        String tag_json_arry = "json_array_req";
        String url = "http://coms-309-nv-4.misc.iastate.edu:8080/gamelist";
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    public ArrayList<Game> gameArrayList = new ArrayList<>();

                    public LinkedGameList linkedGameList1 = linkedGameList;
                    public void updateLinkedGameList(LinkedGameList updateList)
                    {
                        linkedGameList = updateList;
                    }

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONArray responseNames;
                            try {
                                JSONObject responseJSONObject = response.getJSONObject(i);
                                responseNames = responseJSONObject.names();

                                Game game = new Game();
                                game.setTitle(responseJSONObject.getString(responseNames.get(0).toString()));
                                game.setBlurb(responseJSONObject.getString(responseNames.get(1).toString()));
                                game.setAbout(responseJSONObject.getString(responseNames.get(2).toString()));
                                game.setGameID(responseJSONObject.getString(responseNames.get(3).toString()));
                                game.setCreatorID(responseJSONObject.getString(responseNames.get(4).toString()));
                                gameArrayList.add(game);

                                /*
                                0=Title
                                1=blurb
                                2=about
                                3=gameID
                                4=Creator ID
                                5=thumbnail
                                 */
                            } catch (JSONException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        linkedGameList1 = createLinkedList(gameArrayList);
                        updateLinkedGameList(linkedGameList1);
                        displayGames(gameArrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

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

    private void displayGames(List<Game> gameList) {
        for (Game game : gameList) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutGameList);
            TextView titleTextView = new TextView(getApplicationContext());
            titleTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String title = game.getTitle();
            titleTextView.setText(title + "\n");
            titleTextView.setTextSize(20);
            linearLayout.addView(titleTextView);

            TextView blurbTextView = new TextView(getApplicationContext());
            blurbTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String blurb = game.getBlurb();
            blurbTextView.setText(blurb);
            blurbTextView.setTextSize(15);
            linearLayout.addView(blurbTextView);

            TextView aboutTextView = new TextView(getApplicationContext());
            aboutTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String about = game.getAbout();
            aboutTextView.setText("Description:" + about);
            aboutTextView.setTextSize(15);
            linearLayout.addView(aboutTextView);

            TextView gameIDTextView = new TextView(getApplicationContext());
            gameIDTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String gameID = game.getGameID();
            gameIDTextView.setText("Game ID:" + gameID);
            gameIDTextView.setTextSize(15);
            linearLayout.addView(gameIDTextView);

            TextView creatorIDTextView = new TextView(getApplicationContext());
            creatorIDTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            String creatorID = game.getCreatorID();
            creatorIDTextView.setText("Created by:" + creatorID + "\n");
            creatorIDTextView.setTextSize(15);
            linearLayout.addView(creatorIDTextView);
        }
    }
}
