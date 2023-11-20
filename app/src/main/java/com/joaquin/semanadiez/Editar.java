package com.joaquin.semanadiez;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;

public class Editar extends AppCompatActivity {

    private EditText edNombre,edApellido,edEdad,edId, edPass;
    private Button b_editar,b_eliminar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        edNombre = findViewById(R.id.nombre);
        edApellido = findViewById(R.id.apellido);
        edEdad = findViewById(R.id.edad);
        edPass = findViewById(R.id.pass);
        edId = findViewById(R.id.id);

        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btnEliminar);


        Intent i = getIntent();

        String et_id = i.getStringExtra("id").toString();
        String et_nombre = i.getStringExtra("nombre").toString();
        String et_apellido = i.getStringExtra("apellido").toString();
        String et_edad = i.getStringExtra("edad").toString();
        String et_pass = i.getStringExtra("contraseña").toString();

        edId.setText(et_id);
        edNombre.setText(et_nombre);
        edApellido.setText(et_apellido);
        edEdad.setText(et_edad);
        edPass.setText(et_pass);

        b_editar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                editar();
            }
        });

        b_eliminar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                eliminar();
            }
        });

    }

    public void eliminar()
    {
        try
        {
            String id = edId.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO", Context.MODE_PRIVATE,null);


            String sql = "delete from persona where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,id);
            statement.execute();
            Toast.makeText(this,"Datos eliminados de la base de datos.",Toast.LENGTH_LONG).show();

            edNombre.setText("");
            edApellido.setText("");
            edEdad.setText("");
            edPass.setText("");
            edNombre.requestFocus();

        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }
    public void editar()
    {
        try
        {
            String nombre = edNombre.getText().toString();
            String apellido = edApellido.getText().toString();
            String edad = edEdad.getText().toString();
            String id = edId.getText().toString();
            String pass = edPass.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_EJEMPLO",Context.MODE_PRIVATE,null);

            String sql = "update persona set nombre = ?,apellido=?,edad=?,pass=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombre);
            statement.bindString(2,apellido);
            statement.bindString(3,edad);
            statement.bindString(4,pass);
            statement.bindString(5,id);
            statement.execute();
            Toast.makeText(this,"Datos actualizados satisfactoriamente en la base de datos.",Toast.LENGTH_LONG).show();

            edNombre.setText("");
            edApellido.setText("");
            edEdad.setText("");
            edPass.setText("");
            edNombre.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }


}
