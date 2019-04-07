package com.example.sebastian.aplicacionarduinonuevo.controller;

import android.content.ContentValues;
import android.content.Context;

import com.example.sebastian.aplicacionarduinonuevo.model.UsuarioDBContract;
import com.example.sebastian.aplicacionarduinonuevo.model.UsuarioModel;

import static java.lang.String.valueOf;

/**
 * Created by Sebastian on 04-06-2018.
 */

public class UsuarioController {

    //ACCEDER A LA BASE DE DATOS MEDIANTE ESTE OBJETO
    private UsuarioModel usuarioModel;



    public UsuarioController(Context context){this.usuarioModel = new UsuarioModel(context);}

    public void crearUsuario(Integer idUSER,String username,String apellido1,String apellido2, String password)throws Exception{

        ContentValues usuario=new ContentValues();

        usuario.put(UsuarioDBContract.ProfesorTabla._ID,idUSER);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_USERNAME,username);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO,apellido1);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_APELLIDO2,apellido2);
        usuario.put(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD,password);


        this.usuarioModel.crearUsuario(usuario);
    }

    public boolean obtenerCount(){


        if (this.usuarioModel.obtenerCantidad()==0){
            return false;
        }else
        {
            return true;
        }

    }

    public boolean eliminarRegistros(){

        this.usuarioModel.eliminarRegistros();

        return true;
    }

    public String obtenerAPELLIDOusuario(String username){

        String usuario = valueOf(this.usuarioModel.obtenerApellido1PorUsername(username));

        String usuarioN = usuario.replaceFirst("apellido=", "");


        return usuarioN;
    }

    public String obtenerAPELLIDO2usuario(String username){

        String usuario = String.valueOf(this.usuarioModel.obtenerApellido2PorUsername(username));


        String usuarioN = usuario.replaceFirst("apellido2=", "");

        return usuarioN;
    }

    public String obtenerIDusuario(String username){

        String usuario = valueOf(this.usuarioModel.obtenerIdPorUsername(username));


        String usuarioN=usuario.replaceFirst("_id=","");


        return usuarioN;
    }

    public String obtenerUSERNAMEporID(String id){

        String usuario = valueOf(this.usuarioModel.obtenerUsernamePorID(id));


        String usuarioN=usuario.replaceFirst("name=","");
        String usuarioM=usuarioN.replaceFirst("apellido=","");


        return usuarioM;
    }


    public boolean usuarioLogin(String username, String password){
        //PEDIR DATOS DEL USUARIO
        ContentValues usuario= this.usuarioModel.obtenerUsuarioPorUsername(username);

        if (usuario ==null){

            return false;

        }

        if (password.equals(usuario.get(UsuarioDBContract.ProfesorTabla.COLUMN_NAME_PASSWORD))){

            return true;

        }

        return false;
    }


}
