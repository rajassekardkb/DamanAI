package com.example.damanhacker.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.damanhacker.R;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceDaman extends Service {
    String CHANNEL_ID = "qdj_sgdys";
    int NOTIFICATION_ID = 149;
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your service here
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform the main logic of your service here
        // Return a proper value based on your requirements
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Your Service Name").setContentText("Your Service is running").setSmallIcon(R.drawable.ic_menu_camera).build();
        // showToast("This start!");
        //startForeground(NOTIFICATION_ID, notification);
        //startTimer();
        return START_STICKY;
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showToast("This is your toast message!");
            }
        }, 0, 1 * 60 * 1000); // 10 minutes in milliseconds
    }

    private void showToast(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}