package com.example.truckers.view.ui.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.truckers.model.Alarm;

public class AlarmReceiver extends BroadcastReceiver {

        Alarm alarm;

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent notification = new Intent(context, NotificationService.class);
            notification.putExtra(alarm.name, alarm.message);
            notification.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            ContextCompat.startForegroundService(context, notification );

            Log.d("TRUCKER", " ALARM RECEIVED!!!");

        }
}
