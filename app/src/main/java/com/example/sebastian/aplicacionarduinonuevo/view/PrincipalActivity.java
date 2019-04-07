package com.example.sebastian.aplicacionarduinonuevo.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sebastian.aplicacionarduinonuevo.MainActivity;
import com.example.sebastian.aplicacionarduinonuevo.R;
import com.example.sebastian.aplicacionarduinonuevo.controller.UsuarioController;
import com.example.sebastian.aplicacionarduinonuevo.model.UsuarioDBContract;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Sebastian on 15-06-2018.
 */

public class PrincipalActivity  extends AppCompatActivity {

    private Button buttonAbrir,buttonAbrir2,buttonAbrir3;
    private String web_service ="http://192.168.1.200/ABRIR";
    private String web_service2="http://192.168.1.201/ABRIR";
    private String web_service3="http://192.168.1.202/ABRIR";



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_salir, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_salir:

                final AlertDialog.Builder alerta=new AlertDialog.Builder(this);
                alerta.setMessage("Realmente desea cerrar sesion?");
                alerta.setTitle("Cerrar sesion");
                alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sesiones = getSharedPreferences(UsuarioDBContract.ProfesorSesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sesiones.edit();

                        editor.putBoolean(UsuarioDBContract.ProfesorSesion.FIELD_SESSION, false);
                        editor.putString(UsuarioDBContract.ProfesorSesion.FIELD_USERNAME, "");
                        editor.commit();

                        Intent i = new Intent(PrincipalActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog=alerta.create();
                dialog.show();




                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logocompletoblanco);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_principal);



        //NOMBRE DOCENTE
        final TextView textNAME =(TextView) findViewById(R.id.textDocente);

        //PUERTA1
        this.buttonAbrir = (Button) findViewById(R.id.abrirbtn);
        final TextView textUltimo=(TextView) findViewById(R.id.textUltimo);


        //PUERTA2
        this.buttonAbrir2=(Button) findViewById(R.id.abrirbtn2);
        final TextView textUltimo2 =(TextView) findViewById(R.id.textUltimo2);


        //PUERTA3
        this.buttonAbrir3=(Button)findViewById(R.id.abrirbtn3);
        final TextView textUltimo3 =(TextView) findViewById(R.id.textUltimo3);

        final RequestQueue queue = Volley.newRequestQueue(this);




        final String urlR= "http://192.168.1.101/DoorSystem/public/api/registro";

        final String urlS1= "http://192.168.1.101/DoorSystem/public/api/sala"; //40.121.70.134/API





        SharedPreferences sesiones=getSharedPreferences(UsuarioDBContract.ProfesorSesion.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        final String idU = (sesiones.getString(UsuarioDBContract.ProfesorSesion.FIELD_ID,"IDUSUARIO"));
        String nombreD = sesiones.getString(UsuarioDBContract.ProfesorSesion.FIELD_USERNAME,"NOMBREUSUARIO");
        String apellidoD=sesiones.getString(UsuarioDBContract.ProfesorSesion.FIELD_APELLIDO1,"APELLIDOUSUARIO");
        String apellidoD2=sesiones.getString(UsuarioDBContract.ProfesorSesion.FIELD_APELLIDO2,"APELLIDOUSUARIO2");


        textNAME.setText(nombreD+" "+apellidoD+" "+apellidoD2);

        final UsuarioController controller = new UsuarioController(getApplicationContext());



        final JsonArrayRequest getRequest2 = new JsonArrayRequest(Request.Method.GET, urlS1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String asd = response.toString();
                        try {
                            JSONArray json = new JSONArray(asd);

                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);
                                String idSala =o.getString("id");
                                String estado = o.getString("Estado");

                                //SALA1
                                if (Integer.parseInt(idSala)==1 &&Integer.parseInt(estado)==0){


                                    //SALA CERRADA

                                    buttonAbrir.setEnabled(true);

                                    buttonAbrir.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.boton_redondo_abrir));

                                    buttonAbrir.setText("ABRIR");

                                    buttonAbrir.setTextColor(Color.BLACK);

                                }else if (Integer.parseInt(idSala)==1 &&Integer.parseInt(estado)==1){


                                    //SALA ABIERTA


                                    buttonAbrir.setEnabled(false);

                                    buttonAbrir.setBackgroundColor(Color.GRAY);

                                    buttonAbrir.setText("ABIERTA");

                                    buttonAbrir.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.boton_redondo_abierta));



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

        queue.add(getRequest2);

        final JsonArrayRequest getRequest1 = new JsonArrayRequest(Request.Method.GET, urlR, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String asd = response.toString();
                        try {
                            JSONArray json = new JSONArray(asd);

                            for (int i = 0; i < json.length(); i++) {

                                JSONObject o = json.getJSONObject(i);
                                String idSala= o.getString("sala_id");
                                String idUltimo = o.getString("user_id");

                                if (Integer.parseInt(idSala)==1) {
                                    textUltimo.setText(controller.obtenerUSERNAMEporID(idUltimo));

                                }else if(Integer.parseInt(idSala)==2){
                                    textUltimo2.setText(controller.obtenerUSERNAMEporID(idUltimo));
                                }else if(Integer.parseInt(idSala)==3){
                                    textUltimo3.setText(controller.obtenerUSERNAMEporID(idUltimo));
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
        queue.add(getRequest1);


        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alerta= new AlertDialog.Builder(PrincipalActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.abrir_puerta_dialog,null);

                Button buttonConfirmar=(Button)mView.findViewById(R.id.btnAbrirConfirmar);

                alerta.setView(mView);
                final AlertDialog dialog = alerta.create();
                dialog.show();

                buttonConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        final String url2 = "http://192.168.1.101/DoorSystem/public/api/registro";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("Response", response);
                                       // COLOCAR AQUI MENSAJE DE ENVIADO

                                        Toast.makeText(PrincipalActivity.this, "ABIERTA", Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();




                                        new AsyncTask<Void, Void, Void>() {

                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                HttpClient httpclient = new DefaultHttpClient();
                                                try {



                                                    httpclient.execute(new HttpGet(web_service));




                                                } catch (Exception e) {

                                                    e.printStackTrace();
                                                }

                                                return null;
                                            }


                                        }.execute();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.d("Error.Response", error.toString());
                                        Toast.makeText(PrincipalActivity.this, "ERROR AL ABRIR", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();

                                params.put("user_id",idU);
                                params.put("sala_id","1");



                                return params;
                            }
                        };
                        queue.add(postRequest);




                    }


                });







            }
        });



        //BOTON SALA 2
        buttonAbrir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alerta= new AlertDialog.Builder(PrincipalActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.abrir_puerta_dialog,null);

                Button buttonConfirmar2=(Button)mView.findViewById(R.id.btnAbrirConfirmar);

                alerta.setView(mView);
                final AlertDialog dialog = alerta.create();
                dialog.show();

                buttonConfirmar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        final String url2 = "http://192.168.1.101/DoorSystem/public/api/registro";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("Response", response);
                                        // COLOCAR AQUI MENSAJE DE ENVIADO

                                        Toast.makeText(PrincipalActivity.this, "ABIERTA", Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();




                                        new AsyncTask<Void, Void, Void>() {

                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                HttpClient httpclient = new DefaultHttpClient();
                                                try {



                                                    httpclient.execute(new HttpGet(web_service2));




                                                } catch (Exception e) {

                                                    e.printStackTrace();
                                                }

                                                return null;
                                            }


                                        }.execute();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.d("Error.Response", error.toString());
                                        Toast.makeText(PrincipalActivity.this, "ERROR AL ABRIR", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();

                                params.put("user_id",idU);
                                params.put("sala_id","2");



                                return params;
                            }
                        };
                        queue.add(postRequest);




                    }


                });







            }
        });



        //BOTON SALA 3
        buttonAbrir3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alerta= new AlertDialog.Builder(PrincipalActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.abrir_puerta_dialog,null);

                Button buttonConfirmar3=(Button)mView.findViewById(R.id.btnAbrirConfirmar);

                alerta.setView(mView);
                final AlertDialog dialog = alerta.create();
                dialog.show();

                buttonConfirmar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        final String url2 = "http://192.168.1.101/DoorSystem/public/api/registro";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("Response", response);
                                        // COLOCAR AQUI MENSAJE DE ENVIADO

                                        Toast.makeText(PrincipalActivity.this, "ABIERTA", Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();




                                        new AsyncTask<Void, Void, Void>() {

                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                HttpClient httpclient = new DefaultHttpClient();
                                                try {



                                                    httpclient.execute(new HttpGet(web_service3));




                                                } catch (Exception e) {

                                                    e.printStackTrace();
                                                }

                                                return null;
                                            }


                                        }.execute();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.d("Error.Response", error.toString());
                                        Toast.makeText(PrincipalActivity.this, "ERROR AL ABRIR", Toast.LENGTH_SHORT).show();
                                    }
                                }

                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();

                                params.put("user_id",idU);
                                params.put("sala_id","3");



                                return params;
                            }
                        };
                        queue.add(postRequest);




                    }


                });







            }
        });



        Thread t= new Thread(){
            @Override
            public void run() {
                while (!interrupted()) {
                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {




                                queue.add(getRequest2);
                                queue.add(getRequest1);




                            }
                        });
                    } catch (Exception e) {
                        Log.d("error thread", e.toString());
                    }
                }
            }
        };
        t.start();
    }


}

