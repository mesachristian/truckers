package com.example.truckers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.truckers.model.Alarm;
import com.example.truckers.provider.alarm.AlarmProvider;
import com.example.truckers.view.ui.HomeNotLoggedInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.joda.time.DateTime;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        String hour, minute;

        hour = settings.getString("hour","");
        minute = settings.getString("minute","");
        AlarmProvider.sensorAlarm.message = "Su pulso esta demasiado bajo";

        createAlarm(DateTime.now().getHourOfDay(),DateTime.now().getMinuteOfDay() + 1);


        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                Intent intent;
                if(currentUser == null){
                    AlarmProvider.createAlarm(getApplicationContext());
                    intent = new Intent(MainActivity.this, HomeNotLoggedInActivity.class);

                }else{

                    intent = new Intent(MainActivity.this, HomeDrawer.class);
                }
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    public void createAlarm(int selectedHour, int selectedMinute){

        String finalHour, finalMinute;

        finalHour = "" + selectedHour;
        finalMinute = "" + selectedMinute;

        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, selectedHour);
        today.set(Calendar.MINUTE, selectedMinute);
        today.set(Calendar.SECOND, 0);

        SharedPreferences.Editor edit = settings.edit();
        edit.putString("hour", finalHour);
        edit.putString("minute", finalMinute);

        //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
        edit.putInt("alarmID", 1);
        edit.putLong("alarmTime", today.getTimeInMillis());

        edit.commit();

        //Toast.makeText(MainActivity.this, finalHour + ":" + finalMinute, Toast.LENGTH_LONG).show();

        AlarmProvider.setAlarm(1, today.getTimeInMillis(), MainActivity.this);
    }

}