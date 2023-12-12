package com.joaquin.semanadiez;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;



import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity {






    MediaPlayer mp;
    ImageButton btnLogin, btnIrFB;
    ImageButton btnMapa, mqttActivation;


    //  mqtt://mqtt-perrito-sonriente:8uAFhVex5I3hXUiO@mqtt-perrito-sonriente.cloud.shiftr.io


    //  mqtt://otro-perro:E5RaDQiLgE9z7nHG@otro-perro.cloud.shiftr.io
    private static final String mqttHost = "tcp://perrito-sonriente.cloud.shiftr.io:1883";
    private static final String mqttUser = "mqtt-perrito-sonriente";
    private static final String mqttPass = "8uAFhVex5I3hXUiO";
    private MQTTActivity mqttHandler;
    private FirebaseAnalytics mFirebaseAnalytics;

    public boolean estadoMQTT = false;

    public String nombre_dispositivo;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, SensorActivity.class);
        startService(serviceIntent);

        obtener_nombre_Dispositivo();

        mqttHandler = new MQTTActivity();

        mqttActivation = findViewById(R.id.btnMQTTA);

        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.colorRojo);

        mqttHandler.setCallback(new MQTTActivity.MqttCallback() {
            @Override
            public void onSubscribeSuccess() {
                showToast("Suscripción Exitosa");
            }

            @Override
            public void onSubscribeFailure(String errorMessage) {
                showToast("Error al intentar suscribirse: " + errorMessage);
            }
        });

        //mqttHandler.connect("tcp://mqtt://otro-perro:E5RaDQiLgE9z7nHG@otro-perro.cloud.shiftr.io:1883", "mqtt://otro-perro");


        //mqttHandler.subscribe("gatos");

        conexionBroker();




        btnMapa = (ImageButton)findViewById(R.id.btnMapa);
        btnLogin = (ImageButton)findViewById(R.id.btnLogin);
        btnIrFB = (ImageButton)findViewById(R.id.btnIrFB);
        mp = MediaPlayer.create(this, R.raw.btn1);
        mp = MediaPlayer.create(this, R.raw.btn1);










        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mp.start();
                VerLogin();
            }
        });





        btnIrFB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mp.start();

                IrFB();
            }
        });





        btnMapa.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mp.start();
                IrMapa();
            }
        });




        mqttActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstado();

                //MQTTActivity mqttHandler2 = new MQTTActivity();

                try {

                    mqttHandler.setCallback(new MQTTActivity.MqttCallback() {
                        @Override
                        public void onSubscribeSuccess() {
                            showToast("Suscripción Exitosa");

                            //mqttHandler2.connect(mqttHost, mqttUser);

                            mqttHandler.publish("gatos", "Hola desde la aplicación!");


                        /*class abrirPublishAsync extends AsyncTask<Void, Void, Void>{

                            @Override
                            protected Void doInBackground(Void... voids) {
                                try {
                                    mqttHandler2.publish("gatos","hola");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }*/
                        }

                        @Override
                        public void onSubscribeFailure(String errorMessage) {
                            showToast("Error al intentar suscribirse: " + errorMessage);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("MQTT", "Error al conectar: " + e.getMessage());
                }





            }
        });



    }




    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {

        super.onDestroy();


        Intent serviceIntent = new Intent(this, SensorActivity.class);
        stopService(serviceIntent);
        mqttHandler.disconnect();


    }






    public void IrMapa(){

        Intent i = new Intent(MainActivity.this, MapaActivity.class);
        startActivity(i);


    }


    public void IrFB(){

        Intent i = new Intent(MainActivity.this, FB_RegisterActivity.class);
        startActivity(i);


    }


    public void VerLogin(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);

    }



    private void UnSegundo(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){}
    }




    private class EjemploAsyncTask extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            for(int i=1; i<=10; i++){
                UnSegundo();
                publishProgress(i*10);
                if(isCancelled()){
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aVoid);
            if(resultado){
                Toast.makeText(getBaseContext(), "Tarea Larga Finalizada en AsyncTask", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getBaseContext(), "Tarea Larga Ha sido cancelada", Toast.LENGTH_LONG).show();
        }
    }

    public void cambiarEstado(){

        if (estadoMQTT != false){
            estadoMQTT = false;
            mqttActivation.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorVerde));

        }else {
            estadoMQTT = true;
            mqttActivation.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRojo));
        }



    }

    public void obtener_nombre_Dispositivo(){

        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        String nombre_Dispositivo = fabricante+" "+modelo;
        TextView tvNombreDispositivo = (TextView) findViewById(R.id.txtNombreDispositivo);//para enlazar el tv_G con el codigo
        tvNombreDispositivo.setText(nombre_Dispositivo);//para mostrar en el tv_g e modelo del celular
    }


    public void conexionBroker(){
        String clientId = nombre_dispositivo;
        MqttAndroidClient cliente = new MqttAndroidClient(this.getApplicationContext(), mqttHost, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttUser);
        options.setPassword(mqttPass.toCharArray());

        try {
            IMqttToken token = cliente.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    showToast("Conectado");


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    showToast("No conectado");

                }
            });

        }catch (MqttException e){
            e.printStackTrace();

        }


    }







}