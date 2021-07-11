package com.example.standupchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private Button btnNextAlarm;

    // Notification ID.
    private static final int NOTIFICATION_ID = 0;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        btnNextAlarm = findViewById(R.id.next_alarm);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;

                if(isChecked) {
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
                    }

                    //Set the toast message for the "on" case
                    toastMessage = getString(R.string.stand_up_on);
                } else {
                    //Cancel notification if the alarm is turned off
                    mNotificationManager.cancelAll();

                    if(alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }

                    //Set the toast message for the "off" case
                    toastMessage = getString(R.string.stand_up_off);
                }

                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT).show();
            }
        });

        btnNextAlarm.setOnClickListener((view) -> {
            long triggerTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES;

            AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(triggerTime, notifyPendingIntent);

            alarmManager.setAlarmClock(info, notifyPendingIntent);

            String message;

            if (alarmManager.getNextAlarmClock() != null) {
                Date nextAlarm = new Date(alarmManager.getNextAlarmClock().getTriggerTime());

                String newDate = DateFormat.getTimeInstance().format(nextAlarm);

                message = "The alarm is set for " + newDate;
            }
            else {
                message = "There is no alarm scheduled.";
            }

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

        });

        createNotificationChannel();
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(getString(R.string.channel_desc));

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}