package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CustomService extends Service {
    public CustomService() { }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();

        Log.i("CustomService", "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

        Log.i("CustomService", "Service started");

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();

        Log.i("CustomService", "Service stopped");
    }
}