package com.joaquin.semanadiez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.joaquin.semanadiez.SQL.Persona;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {


    private ListView lst1;
    private ArrayList<String> arreglo = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;

    private boolean isPlaying = false;

    ImageButton btnStop, btnPause, btnPlay;



    MediaPlayer mp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        mp = MediaPlayer.create(this, R.raw.indagetto_jbalvin);
        mp.start();


        btnStop = findViewById(R.id.btnStop);
        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setVisibility(View.GONE);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mp.stop();

                //comprobarVisible();

                if (isPlaying) {

                    detenerReproduccion();
                } else {

                    iniciarReproduccion();
                }






                /*if (btnPlay.getVisibility() == View.GONE){


                    btnPlay.setVisibility(View.VISIBLE);


                }
                btnStop.setVisibility(View.GONE);*/


            }
        });





        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.pause();


            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //comprobarVisible();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            new ReproducirAudioAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            new ReproducirAudioAsync().execute();
                        }
                    }
                }, 1000);

            }

        });



        try{
            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE,null);
            lst1 = findViewById(R.id.lst1);
            final Cursor c = db.rawQuery("select * from persona",null);
            int id = c.getColumnIndex("id");
            int nombre = c.getColumnIndex("nombre");
            int apellido = c.getColumnIndex("apellido");
            int edad = c.getColumnIndex("edad");
            int pass = c.getColumnIndex("pass");
            arreglo.clear();

            arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arreglo);

            lst1.setAdapter(arrayAdapter);

            final  ArrayList<Persona> lista = new ArrayList<Persona>();


            if(c.moveToFirst())
            {
                do{
                    Persona persona = new Persona();
                    persona.id = c.getString(id);
                    persona.nombre = c.getString(nombre);
                    persona.apellido = c.getString(apellido);
                    persona.edad = c.getString(edad);
                    persona.pass = c.getString(pass);
                    lista.add(persona);

                    arreglo.add(c.getString(id) + " \t " + c.getString(nombre) + " \t "  + c.getString(apellido) + " \t "  + c.getString(edad)+ " \t "  + c.getString(pass) );

                } while(c.moveToNext());
                arrayAdapter.notifyDataSetChanged();
                lst1.invalidateViews();
            }

        }


        catch (Exception e){
            Toast.makeText(this, "Ha ocurrido un error, intentalo nuevamente.", Toast.LENGTH_SHORT).show();



        }



    }

    private void iniciarReproduccion() {
        btnStop.setImageResource(R.drawable.stop);
        isPlaying = true;
        new ReproducirAudioAsync().execute();
    }

    private void detenerReproduccion() {
        btnStop.setImageResource(R.drawable.play);
        isPlaying = false;
        mp.stop();
        mp.reset();
        mp = MediaPlayer.create(this, R.raw.indagetto_jbalvin);
    }






    /*public void comprobarVisible(){
        if (btnStop.getVisibility() == View.VISIBLE){


            btnPlay.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);

        }else {

            btnPlay.setVisibility(View.GONE);

            btnStop.setVisibility(View.VISIBLE);
        }



    }*/


    private class ReproducirAudioAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mp.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }








}