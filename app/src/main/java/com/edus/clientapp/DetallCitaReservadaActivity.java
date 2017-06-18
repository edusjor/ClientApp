package com.edus.clientapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.Activities.MenuActivity;
import com.edus.clientapp.Activities.MostrarTodasCitasActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallCitaReservadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_cita_reservada);
    }

    public void cancelar(View view) throws JSONException {

        enviardatos();

        Toast toast2 = Toast.makeText(getApplicationContext(),"Cita Cancelada", Toast.LENGTH_LONG);
        toast2.show();

        Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(iralmenu);

    }

    public void retroceder(View view){
        Intent atras= new Intent(getApplicationContext(), MostrarTodasCitasActivity.class);
        startActivity(atras);
    }

    public void irAMenu(View view){
        Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(iralmenu);
    }






    private void enviardatos() throws JSONException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String serverIP = pref.getString("serverIp", "nulo");
        String idCita = pref.getString("currendiDcita", "nulo");




        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverIP+"/mavenproject1/webapi/crearCita/cancelarCita?idCita="+idCita;

        final JSONObject obj = new JSONObject();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response is: ", response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getCause();

            }
        });
        queue.add(stringRequest);
    }
}
