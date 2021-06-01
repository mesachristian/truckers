package com.example.truckers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.truckers.model.Alarm;
import com.example.truckers.view.ui.MapsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_alarm);
        getSupportActionBar().setTitle("Nueva Alarma");
    }

    public void addAlarm(View v){

        startActivity(new Intent(this, AlarmActivity.class));
    }



}