package com.joaquin.semanadiez;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {



    MediaPlayer mp;
    Button btnLogin;
    Button btnMapa;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, SensorActivity.class);
        startService(serviceIntent);

        btnMapa = (Button)findViewById(R.id.btnMapa);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        mp = MediaPlayer.create(this, R.raw.btn1);
        mp = MediaPlayer.create(this, R.raw.btn1);
        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mp.start();
                VerLogin();
            }
        });


        btnMapa.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mp.start();
                IrMapa();
            }
        });



    }

    protected void onDestroy() {

        super.onDestroy();


        Intent serviceIntent = new Intent(this, SensorActivity.class);
        stopService(serviceIntent);

    }






    public void IrMapa(){

        Intent i = new Intent(MainActivity.this, MapaActivity.class);
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



}