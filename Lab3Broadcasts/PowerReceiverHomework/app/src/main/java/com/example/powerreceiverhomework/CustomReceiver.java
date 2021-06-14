package com.example.powerreceiverhomework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    private static final String EXTRA_RANDOM_NUMBER = "RANDOM_NUMBER";
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMessage = "unknown intent action";
            String stringNumber = intent.getStringExtra(EXTRA_RANDOM_NUMBER);
            int number = Integer.valueOf(stringNumber);

            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
            }

            //Display the toast.
            Toast.makeText(context, toastMessage + ". Square of the Random number: " + number * number, Toast.LENGTH_SHORT).show();
        }
    }
}