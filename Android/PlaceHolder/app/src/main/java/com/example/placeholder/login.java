package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity
{
    Button login;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.usernameEntry);
        password = (EditText)findViewById(R.id.passwordEntry);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(username.getText().toString().equals("a username object from online") && password.getText().toString().equals("password object"))
                {
                    openHomePage();
                }
            }
        });


    }
    public void openHomePage()
    {
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
    }
}
