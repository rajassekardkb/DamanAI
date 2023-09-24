package com.example.damanhacker.service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.damanhacker.R;

public class ServiceActivity extends AppCompatActivity {
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    private static final String TAG = ServiceActivity.class.getSimpleName();

    public Context getCtx() {
        return ctx;
    }

    //private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("fdsdtu", MODE_PRIVATE);


        //finishButton = findViewById(R.id.finishButton);

        ctx = this;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(TAG, "isMyServiceRunning? " + true + "");
                return true;
            }
        }

        Log.i(TAG, "isMyServiceRunning? " + false + "");
        return false;
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        // ondestroy service not being called
        stopService(mServiceIntent);

        super.onDestroy();
    }
}
