package com.joaquin.semanadiez;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    private ProgressBar progressBar2;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent(this, SensorActivity.class);
        startService(serviceIntent);





    }




    public void IrMapa(View view){

        Intent i = new Intent(MainActivity.this, MapaActivity.class);
        startActivity(i);


    }


    public void VerLogin(View view){
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
            progressBar.setMax(100);
            progressBar.setProgress(0);
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

            progressBar.setProgress(values[0].intValue());
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