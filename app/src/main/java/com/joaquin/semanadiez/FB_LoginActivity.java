package com.joaquin.semanadiez;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FB_LoginActivity extends AppCompatActivity {

    Button btnGoRegis;
    ImageButton BtnHide;

    EditText eTextMail, eTextPass;


    public boolean isHide = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_login);

        btnGoRegis = findViewById(R.id.btnRegisterFB);
        BtnHide = findViewById(R.id.BtnHide);
        eTextMail = findViewById(R.id.etCorreoFB);
        eTextPass = findViewById(R.id.etPassFB);


        btnGoRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        TextView goRegis = findViewById(R.id.btnRegisterFB);
        goRegis.setPaintFlags(goRegis.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }



    public void HidedPass(View view){
        if (isHide == true){
            BtnHide.setImageResource(R.drawable.eye_off);
            eTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isHide = false;
        }else {
            BtnHide.setImageResource(R.drawable.eye_on);
            eTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isHide = true;
        }




    }





}