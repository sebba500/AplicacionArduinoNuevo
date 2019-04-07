package com.example.sebastian.aplicacionarduinonuevo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sebastian.aplicacionarduinonuevo.controller.UsuarioController;
import com.example.sebastian.aplicacionarduinonuevo.model.UsuarioDBContract;
import com.example.sebastian.aplicacionarduinonuevo.view.PrincipalActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private Button buttonLogin;
    private EditText editTextUsername, editPassword;
    private TextView pu, pp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logocompletoblanco);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main);






        SharedPreferences sesion = getSharedPreferences(UsuarioDBContract.ProfesorSesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = sesion.getBoolean(UsuarioDBContract.ProfesorSesion.FIELD_SESSION, false);
        if (loggedIn) {
            Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(i);
            finish();
        }

        this.buttonLogin = (Button) findViewById(R.id.btLogin);
        this.editTextUsername = (EditText) findViewById(R.id.etUsername);
        this.editPassword = (EditText) findViewById(R.id.etPassword);






        final String url = "http://192.168.1.101/DoorSystem/public/api/profesor";

        final RequestQueue queue = Volley.newRequestQueue(this);


        UsuarioController controller1 = new UsuarioController(getApplicationContext());
        try {
            controller1.eliminarRegistros();

        } catch (Exception e) {

        }



        final JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String array = response.toString();
                        try {
                            JSONArray json = new JSONArray(array);
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);
                                String idUsuario = o.getString("id");
                                String nombre = o.getString("name");
                                String apellido1 = o.getString("apellido");
                                String apellido2 = o.getString("apellido2");
                                String clave = o.getString("password");





                                try {
                                    UsuarioController controller = new UsuarioController(getApplicationContext());
                                    controller.crearUsuario(Integer.parseInt(idUsuario), nombre, apellido1, apellido2, clave);
                                    Log.d("CREADO USUARIO", null);
                                } catch (Exception e) {
                                    String mensaje = e.getMessage();
                                    Log.d("NO CREADO", mensaje);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());

                    }
                }
        );


        queue.add(getRequest);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = editTextUsername.getText().toString();
                String password = editPassword.getText().toString();

                UsuarioController controller = new UsuarioController(getApplicationContext());



                if (username.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Ingrese nombre de usuario", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                }
                else if(controller.obtenerCount()==false){

                        Toast.makeText(getApplicationContext(), "No hay conexión con el servidor", Toast.LENGTH_SHORT).show();
                }

                else if (controller.usuarioLogin(username, password)) {


                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);

                    startActivity(intent);

                    SharedPreferences sesion = getSharedPreferences(UsuarioDBContract.ProfesorSesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sesion.edit();

                    editor.putBoolean(UsuarioDBContract.ProfesorSesion.FIELD_SESSION, true);
                    editor.putString(UsuarioDBContract.ProfesorSesion.FIELD_USERNAME, username);

                    editor.putString(UsuarioDBContract.ProfesorSesion.FIELD_ID, controller.obtenerIDusuario(username));

                    editor.putString(UsuarioDBContract.ProfesorSesion.FIELD_APELLIDO1, controller.obtenerAPELLIDOusuario(username));
                    editor.putString(UsuarioDBContract.ProfesorSesion.FIELD_APELLIDO2, controller.obtenerAPELLIDO2usuario(username));


                    editor.commit();

                    finish();
                    ;



                } else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    }



