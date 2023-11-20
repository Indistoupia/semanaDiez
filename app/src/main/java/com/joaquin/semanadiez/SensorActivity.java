package com.joaquin.semanadiez;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

public class SensorActivity extends Service implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private PowerManager powerManager;

    private PowerManager.WakeLock wakeLock;

    MediaPlayer md;


    public class LocalBinder extends Binder {
        SensorActivity getService() {
            return SensorActivity.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);


        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:proximitysong");



    }






    @Override
    public void onDestroy(){
        super.onDestroy();

        sensorManager.unregisterListener(this);
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }


        if (md != null) {
            md.release();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float distance = sensorEvent.values[0];


        float proximityThreshold = 5.0f;

        if (distance < proximityThreshold) {

            if (md != null && md.isPlaying()) {
                md.pause();
            }
        } else {

            if (md != null && !md.isPlaying()) {
                md.start();
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}