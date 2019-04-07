package com.example.sebastian.aplicacionarduinonuevo.model;

import android.provider.BaseColumns;

/**
 * Created by Sebastian on 18-06-2018.
 */

public class UsuarioDBContract {
    private UsuarioDBContract() {}

    /* Columnas de la tabla Usuarios */
    public static class ProfesorTabla implements BaseColumns {
        public static final String TABLE_NAME = "profesor";
        public static final String COLUMN_NAME_USERNAME = "name";
        public static final String COLUMN_NAME_APELLIDO= "apellido";
        public static final String COLUMN_NAME_APELLIDO2= "apellido2";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    public static class ProfesorSesion{
        public static final String SHARED_PREFERENCES_NAME ="sesiones";
        public static final String FIELD_SESSION ="sesion";
        public static final String FIELD_USERNAME="name";
        public static final String FIELD_ID="id";
        public static final String FIELD_APELLIDO1="apellido";
        public static final String FIELD_APELLIDO2="apellido2";

    }
}
