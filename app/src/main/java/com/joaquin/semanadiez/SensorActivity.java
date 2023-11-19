package com.joaquin.semanadiez;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;

public class SensorActivity extends Service implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private PowerManager powerManager;




    @Override
    public void onCreate() {
        super.onCreate();


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float distance = sensorEvent.values[0];



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}