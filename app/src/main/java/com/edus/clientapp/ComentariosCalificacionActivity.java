package com.edus.clientapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.Activities.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ComentariosCalificacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_calificacion);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String idCita = pref.getString("currendiDcita", "nulo");
        String codDoc = pref.getString("currendcodDoc", "nulo");
        final EditText calificationbox;
        final EditText comentariosbox;


        comentariosbox = (EditText) findViewById(R.id.editTextcoment);
        calificationbox = (EditText) findViewById(R.id.editTextcalif);

        Button buttonaceptar = (Button) findViewById(R.id.button6);
        buttonaceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strcalification = calificationbox.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("calificacion", strcalification);
                editor.commit();

                String strcomentario = comentariosbox.getText().toString();
                editor.putString("comentario", strcomentario);
                editor.commit();
                Toast toast2 = Toast.makeText(getApplicationContext(), "comentario Guardada", Toast.LENGTH_SHORT);
                toast2.show();

                try {
                    enviardatos();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), "no se pudo ", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }

        });














    }


    public void irAMenu(View view){
        Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(iralmenu);
    }

    private void enviardatos() throws JSONException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String idDoctor = pref.getString("idDr", "nulo");
        String idPaciente = pref.getString("idPaciente", "nulo");
        String calificacion = pref.getString("calificacion", "");
        String comentario = pref.getString("comentario", "");

        String serverIP = pref.getString("serverIp", "nulo");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverIP+"/mavenproject1/webapi/doctores/calificar";

        final JSONObject obj = new JSONObject();
        obj.put("idDoctor",idDoctor);
        obj.put("idPaciente",idPaciente);
        obj.put("calificacion",calificacion);
        obj.put("comentario",comentario);

        Log.e("comentario",comentario);
        Log.e("id dr",idDoctor);
        Log.e("id Paciente",idPaciente);
        Log.e("calificacion",calificacion);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response is: ", response.toString());
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Datos Enviados"+" Respuesta:  "+response.toString(), Toast.LENGTH_LONG);
                        toast1.show();
                        Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(iralmenu);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getCause();
                Log.e("error ","That didn't work!");
                Toast toast2 = Toast.makeText(getApplicationContext(),"Cita No cancelada", Toast.LENGTH_LONG);
                toast2.show();
                Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(iralmenu);
            }
        });
        queue.add(stringRequest);
    }
}
