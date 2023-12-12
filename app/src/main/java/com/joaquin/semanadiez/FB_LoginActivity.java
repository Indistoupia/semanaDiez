package com.joaquin.semanadiez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class FB_LoginActivity extends AppCompatActivity {

    Button btnGoRegis, btnLoginFB;
    ImageButton BtnHide;


    private MediaPlayer mediaPlayer;



    private FirebaseFirestore db;
    private FirebaseAuth fAuth;





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

        mediaPlayer = MediaPlayer.create(this, R.raw.bamboo_hit);


        btnLoginFB = findViewById(R.id.btnLoginFB);


        btnGoRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btnLoginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = eTextMail.getText().toString().trim();
                String passUser = eTextPass.getText().toString().trim();

                if (emailUser.isEmpty() || passUser.isEmpty()){
                    Toast.makeText(FB_LoginActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    fAuth = FirebaseAuth.getInstance();


                    fAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {








                            mediaPlayer.start();


                            if(task.isSuccessful()){

                                Toast.makeText(FB_LoginActivity.this, "Logeado satisfactoriamente hola profe", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();


                            }else{

                                Toast.makeText(FB_LoginActivity.this, "Inlogeado ono seke", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });



                }
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