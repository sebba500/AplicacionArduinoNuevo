package com.example.sebastian.aplicacionarduinonuevo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sebastian on 18-06-2018.
 */

public class UsuarioDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="prueba";
    public static final int DATABASE_VERSION=2;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsuarioDBContract.ProfesorTabla.TABLE_NAME +
                    "(" + UsuarioDBContract.ProfesorTabla._ID + " INTEGER PRIMARY KEY," +
                    UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME + " TEXT," +
                    UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO + " TEXT," +
                    UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2 + " TEXT," +
                    UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD + " TEXT)";



    public UsuarioDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENTRIES);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
