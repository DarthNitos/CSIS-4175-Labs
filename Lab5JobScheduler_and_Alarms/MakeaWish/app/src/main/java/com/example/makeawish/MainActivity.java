package com.example.makeawish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);

        Intent intent = new Intent(this, AlarmReceiver.class);

        final boolean alarmSet = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_NO_CREATE) != null);
        toggleButton.setChecked(alarmSet);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (alarmManager != null) {
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.HOUR, 11);
                        calendar.set(Calendar.MINUTE, 11);

                        Log.i(getClass().getName(),calendar.getTimeZone() + "");

                        Log.i(getClass().getName(),calendar.getTime() + "");

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                        Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    //Cancel notification if the alarm is turned off
                    mNotificationManager.cancelAll();

                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                    }
                }
            }
        });

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(getString(R.string.channel_desc));

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}