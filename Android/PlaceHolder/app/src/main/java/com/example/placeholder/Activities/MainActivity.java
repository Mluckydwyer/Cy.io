package com.example.placeholder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.placeholder.R;

public class MainActivity extends AppCompatActivity
{
    Button proceedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proceedBtn = findViewById(R.id.MainToLoginButton);
        proceedBtn.setOnClickListener(new View.OnClickListener()
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
}
