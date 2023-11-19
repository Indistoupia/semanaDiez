package com.joaquin.semanadiez.SQL;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Persona {

    @PrimaryKey
    protected String id;

    protected String nombre;
    protected String apellido;
    protected String edad;

}
