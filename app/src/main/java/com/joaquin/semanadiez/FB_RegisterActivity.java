package com.joaquin.semanadiez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    Button btnRegisterFB;
    EditText name, email, password, edad, apellido;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_register);
        this.setTitle("Registro");




        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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
                    registerUser(nameUser, lastnameUser, ageUser, emailUser, passUser);
                }
            }
        });
    }

    private void registerUser(String nameUser, String lastnameUser, String emailUser, String passUser, String ageUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    // Registro exitoso, obtén el usuario
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("nombre", nameUser);
                    map.put("apellido", lastnameUser);
                    map.put("edad", ageUser);
                    map.put("email", emailUser);
                    map.put("password", passUser);

                    mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            startActivity(new Intent(FB_RegisterActivity.this, MainActivity.class));
                            Toast.makeText(FB_RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FB_RegisterActivity.this, "Error al guardar en Firestore", Toast.LENGTH_SHORT).show();
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




}