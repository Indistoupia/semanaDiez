package com.joaquin.semanadiez;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.joaquin.semanadiez.R;

public class RegistroActivity extends AppCompatActivity {

    public boolean isHide = false;

    private ImageView imgView;
 //hola

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        imgView = findViewById(R.id.BtnHide);


    }


    public void HidedPass(View view){
        if (isHide == true){
            imgView.setImageResource(R.drawable.eye_off);
            isHide = false;
        }else {
            imgView.setImageResource(R.drawable.eye_on);
            isHide = true;
        }




    }


    public void IrLogin(View view){
        finish();

    }



}