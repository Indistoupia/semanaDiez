package com.joaquin.semanadiez;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.joaquin.semanadiez.R;

public class RegistroActivity extends AppCompatActivity {

    public boolean isHide = false;

    private EditText etNombre, etApellido, etEdad, etPass;
    public Button btnRegister, btnLogin;
    private ImageView imgView;
 //hola




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        imgView = findViewById(R.id.BtnHide);

        //Edit Texts
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        etPass = findViewById(R.id.etPass);

        btnRegister = findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();


            }
        });


    }



    public void Registrar()
    {
        try
        {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String edad = etEdad.getText().toString();
            String pass = etPass.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS persona(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR,apellido VARCHAR,edad VARCHAR,pass VARCHAR)");

            String sql = "insert into persona(nombre,apellido,edad,pass)values(?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombre);
            statement.bindString(2,apellido);
            statement.bindString(3,edad);
            statement.bindString(4,pass);
            statement.execute();
            Toast.makeText(this,"Datos agregados satisfactoriamente en la base de datos. 🥵🔥",Toast.LENGTH_LONG).show();

            etNombre.setText("");
            etApellido.setText("");
            etEdad.setText("");
            etPass.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error! no se pudieron guardar los datos.😔",Toast.LENGTH_LONG).show();
        }
    }










    public void HidedPass(View view){
        if (isHide == true){
            imgView.setImageResource(R.drawable.eye_off);
            etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isHide = false;
        }else {
            imgView.setImageResource(R.drawable.eye_on);
            etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isHide = true;
        }




    }


    public void IrLogin(View view){
        finish();

    }



}