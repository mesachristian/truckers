package com.example.truckers.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.truckers.R;

public class HomeNotLoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_not_logged_in);

        Button loginBtn = (Button) findViewById(R.id.login_btn_main);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeNotLoggedInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button registerBtn = (Button) findViewById(R.id.register_btn_main);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeNotLoggedInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
