package com.example.truckers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.truckers.view.ui.MapsActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ImageButton backBtn = (ImageButton) findViewById(R.id.home_back_button);
        getSupportActionBar().setTitle("Mis Alarmas");

        TextView text2 = (TextView) findViewById(R.id.editTextTextPersonName4);
        Switch sw2 = (Switch) findViewById(R.id.switch3);
        Log.println(Log.ASSERT,"AA",String.valueOf(text2.getVisibility()));
        if(text2.getVisibility() == 0) {
            Log.println(Log.ASSERT,"AA","AAAA");
            sw2.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
        }

        TextView text = (TextView) findViewById(R.id.editTextTextPersonName4);
        Switch sw = (Switch) findViewById(R.id.switch3);
        sw.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);

    }

    public void showCustomActivity(View v){
        Intent intent = new Intent(AlarmActivity.this, CustomAlarmActivity.class);
        startActivity(intent);
    }
}