package com.example.truckers.view.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.example.truckers.HomeDrawer;
import com.example.truckers.R;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //getLayoutInflater().inflate(R.layout.activity_maps, frameLayout);
        ImageButton backBtn = (ImageButton) findViewById(R.id.home_back_button);
        getSupportActionBar().setTitle("Defina su ruta");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}