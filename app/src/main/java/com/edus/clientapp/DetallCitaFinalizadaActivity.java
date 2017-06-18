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

public class DetallCitaFinalizadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_cita_finalizada);
    }
    public void pagar(View view){
        try {
            enviardatos();
            Toast toast2 = Toast.makeText(getApplicationContext(),"Cita Pagada Satisfactoriamente", Toast.LENGTH_LONG);
            toast2.show();
            Intent calificar= new Intent(getApplicationContext(), ComentariosCalificacionActivity.class);
            startActivity(calificar);
        } catch (JSONException e) {
            e.printStackTrace();
        }






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
        String idCita = pref.getString("currendiDcita", "nulo");
        String serverIP = pref.getString("serverIp", "nulo");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverIP+"/mavenproject1/webapi/crearCita/pagarCita?idCita="+idCita;

        final JSONObject obj = new JSONObject();
        obj.put("idCita",idCita);

        Log.e("id Cita",idCita);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response is: ", response.toString());
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Cita Pagada"+" Respuesta:  "+response.toString(), Toast.LENGTH_LONG);
                        toast1.show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getCause();
                Log.e("error ","That didn't work!");
                Toast toast2 = Toast.makeText(getApplicationContext(),"Error al pagar cita", Toast.LENGTH_LONG);
                toast2.show();
            }
        });
        queue.add(stringRequest);
    }
}
