package com.joaquin.semanadiez;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FB_RegisterActivity extends AppCompatActivity {

    Button btnRegisterFB, btnGoLogin;


    ImageButton BtnHide;

    public boolean isHide = false;



    EditText name, email, password, edad, apellido;

    private FirebaseFirestore db;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_register);
        this.setTitle("Registro");

        TextView goLogin = findViewById(R.id.btnLoginFB);
        goLogin.setPaintFlags(goLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);




        btnGoLogin = findViewById(R.id.btnLoginFB);
        BtnHide = findViewById(R.id.BtnHide);


        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.etNombreFB);
        apellido = findViewById(R.id.etApellidoFB);
        email = findViewById(R.id.etCorreoFB);
        edad = findViewById(R.id.etEdadFB);
        password = findViewById(R.id.etPassFB);
        btnRegisterFB = findViewById(R.id.btnRegisterFB);





        btnRegisterFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = name.getText().toString().trim();
                String lastnameUser = apellido.getText().toString().trim();
                String ageUser = edad.getText().toString().trim();
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if (nameUser.isEmpty() || emailUser.isEmpty() || passUser.isEmpty() || ageUser.isEmpty() || lastnameUser.isEmpty()){
                    Toast.makeText(FB_RegisterActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(nameUser, lastnameUser, emailUser, passUser, ageUser);
                }
            }
        });


        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });


    }







    private void registerUser(String nameUser, String lastnameUser, String emailUser, String passUser, String ageUser) {
        fAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    // Registro exitoso, obtén el usuario
                    String id = fAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("nombre", nameUser);
                    map.put("apellido", lastnameUser);
                    map.put("email", emailUser);
                    map.put("password", passUser);
                    map.put("edad", ageUser);



                    db.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            startActivity(new Intent(FB_RegisterActivity.this, MainActivity.class));
                            Toast.makeText(FB_RegisterActivity.this, "Registro éxitoso!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FB_RegisterActivity.this, "Error al guardar en Firestore", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error escribiendo: ", e);
                        }
                    });
                } else {
                    // Manejar el fallo del registro con Firebase Auth
                    Toast.makeText(FB_RegisterActivity.this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FB_RegisterActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }



    public void goLogin(){
        Intent i = new Intent(FB_RegisterActivity.this, FB_LoginActivity.class);
        startActivity(i);


    }


    public void HidedPass(View view){
        if (isHide == true){
            BtnHide.setImageResource(R.drawable.eye_off);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isHide = false;
        }else {
            BtnHide.setImageResource(R.drawable.eye_on);
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isHide = true;
        }




    }







}