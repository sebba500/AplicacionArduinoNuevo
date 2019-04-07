package com.example.sebastian.aplicacionarduinonuevo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import static com.example.sebastian.aplicacionarduinonuevo.model.UsuarioDBContract.ProfesorTabla.TABLE_NAME;

/**
 * Created by Sebastian on 04-06-2018.
 */

public class UsuarioModel {

    private UsuarioDBHelper dbHelper;

    public UsuarioModel(Context context){this.dbHelper= new UsuarioDBHelper(context);}

    public void crearUsuario(ContentValues usuario){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getWritableDatabase();

        db.insert(TABLE_NAME,null,usuario);
    }

    public ContentValues eliminarRegistros() {


        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);


        return null;
    }

    public int obtenerCantidad(){
        SQLiteDatabase db=this.dbHelper.getWritableDatabase();
        int row = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME);

        return row;
    }


    public ContentValues obtenerIdPorUsername(String username){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                UsuarioDBContract.ProfesorTabla._ID,

        };

        String selection=UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME + " = ? LIMIT 1";
        String[] selectionArgs={username};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR

        String cursor_id= String.valueOf(cursor.getInt(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla._ID)));


        ContentValues usuario=new ContentValues();

        usuario.put(UsuarioDBContract.ProfesorTabla._ID,cursor_id);





        return usuario;
    }




    public ContentValues obtenerUsuarioPorUsername(String username){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                UsuarioDBContract.ProfesorTabla._ID,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD
        };

        String selection=UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME + " = ? LIMIT 1";
        String[] selectionArgs={username};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR
        String cursor_username=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME));
        String cursor_password=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD));

        ContentValues usuario=new ContentValues();
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,cursor_username);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD,cursor_password);

        return usuario;
    }

    public ContentValues obtenerApellido1PorUsername(String username){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                UsuarioDBContract.ProfesorTabla._ID,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD
        };


        String selection=UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME + " = ? LIMIT 1";
        String[] selectionArgs={username};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR
        String cursor_username=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO));


        ContentValues usuario=new ContentValues();
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,cursor_username);


        return usuario;
    }

    public ContentValues obtenerApellido2PorUsername(String username){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                UsuarioDBContract.ProfesorTabla._ID,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD
        };


        String selection=UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME + " = ? LIMIT 1";
        String[] selectionArgs={username};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR
        String cursor_username=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2));


        ContentValues usuario=new ContentValues();
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,cursor_username);


        return usuario;
    }

    public ContentValues obtenerUsernamePorID(String ID){

        //CREAR CONEXION CON BASE DE DATOS
        SQLiteDatabase db= this.dbHelper.getReadableDatabase();

        //DEFINIR COLUMNAS QUE INTERESAN
        String []projection={
                UsuarioDBContract.ProfesorTabla._ID,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,
                UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD
        };


        String selection=UsuarioDBContract.ProfesorTabla._ID + " = ? LIMIT 1";
        String[] selectionArgs={ID};

        //CONSULTA
        Cursor cursor =db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,null,null
        );

        if (cursor.getCount()==0) return null;

        //MOVERSE AL PRIMER RESULTADO
        cursor.moveToFirst();

        //PEDIR LOS DATOS AL CURSOR
        String cursor_username=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME));
        String cursor_apellido=cursor.getString(cursor.getColumnIndex(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO));


        ContentValues usuario=new ContentValues();
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,cursor_username);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,cursor_apellido);


        return usuario;
    }



}
