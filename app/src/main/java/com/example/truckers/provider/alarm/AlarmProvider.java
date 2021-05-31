package com.example.truckers.provider.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.truckers.model.Alarm;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class  AlarmProvider {

    private static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private static SharedPreferences settings;

    private static int alarmID = 1;

    public static Alarm sensorAlarm = new Alarm();

    public static void createUserAlarm(String userId, Alarm alarm) {
        database.child("alarms").child(userId).setValue(alarm);
    }

    public static void CreateDefaultAlarms(){
        Alarm alarm = new Alarm(
                "Paro cardiaco",
                "Usted esta teniendo un Paro Cardiaco debe llamar a emergencias",
                50,
                60);

        database.child("alarms").child("WS066B0TLvcvhZowXkCz44M7lTI2").setValue(alarm);
    }

    public static void createAlarm(Context ctx){

        Calendar currentTime = Calendar.getInstance();

        settings = ctx.getSharedPreferences("Truckers", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = settings.edit();
        edit.putString("hour",  ""+currentTime.get(Calendar.HOUR_OF_DAY));
        edit.putString("minute", ""+(currentTime.get(Calendar.MINUTE)+0.5));

        edit.putInt("alarmID", alarmID);
        edit.putLong("alarmTime", currentTime.getTimeInMillis());

        setAlarm(alarmID, currentTime.getTimeInMillis(), ctx);

        edit.commit();
    }

    public static void setAlarm(int i, Long timestamp, Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
}
