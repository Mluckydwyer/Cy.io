package com.example.volleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bS, bJ, bI;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bS = (Button) findViewById(R.id.btnStringRequest);
        bJ = (Button) findViewById(R.id.btnJsonRequest);
        bI = (Button) findViewById(R.id.btnImageRequest);

        bS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, StringRequest.class);
                startActivity(i);
            }
        });

        bJ.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, JSONActivity.class);
                startActivity(i);
            }
        });

        bI.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, ImageRequest.class);
                startActivity(i);
            }
        });
    }
}
