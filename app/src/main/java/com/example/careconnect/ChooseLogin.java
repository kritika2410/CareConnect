package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLogin extends AppCompatActivity {

    Button parentLogin, userLogin;
    private void init() {
        parentLogin = (Button) findViewById(R.id.parentLogin);
        userLogin = (Button) findViewById(R.id.UserLogin);
//Navigates to parent Login page
        parentLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchParentLoginScreen();
            }
        });
//Navigates to User Login page
        userLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchUserLoginScreen();
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        init();
    }

    private void launchParentLoginScreen()
    {
        Intent intent1 = new Intent(ChooseLogin.this, ParentLogin.class);
        startActivity(intent1);
    }

    private void launchUserLoginScreen()
    {
        Intent intent2 = new Intent(ChooseLogin.this, UserLogin.class);
        startActivity(intent2);
    }
}