package com.edus.clientapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.DetallCitaPagadaActivity;
import com.edus.clientapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCitasPendientesActivity extends AppCompatActivity {
    String data = "";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_citas_pendientes);
        displayView();
    }
    private void displayView() {

        ////////////////////////////////////////////////////////////////
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String serverIP = pref.getString("serverIp", "nulo");
        String idPaciente = pref.getString("idPaciente", "nulo");


        String JsonURL = "http://" + serverIP + "/mavenproject1/webapi/crearCita/consultar?idPaciente="+idPaciente;

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            String estados = "";
                            String fechas = "";
                            String codigoDoctors = "";
                            String idCitas = "";
                            String sintomaS = "";



                            for (int j = 0; j < response.length(); j++){

                                String cita = response.getString(j);
                                String[] parted=cita.split(",");


                                    for (int i=0; i<parted.length; i++){
                                        String [] parted2 = parted[i].split(":");

                                        if (i==0) {
                                            String fecha = parted2[1];
                                            String[] fechaparted = fecha.split("\"");
                                            String fechafinal = fechaparted[0];
                                            fechas += fechafinal + ",";
                                        }
                                            /*
                                        if (i==0){
                                            String estado=parted2[1];
                                            String[] estadoparted=estado.split("\"");
                                            String estadofinal = estadoparted[0];
                                            estados+=estadofinal+",";

                                            }*/
                                        if (i==1){
                                            String estado=parted2[1];
                                            String[] estadoparted=estado.split("\"");
                                            String estadofinal = estadoparted[1];
                                            //String fechafinal = estadofinal;
                                            estados+=estadofinal+",";

                                        }
                                        if (i==2){
                                            String codDoc=parted2[1];
                                            String[] codDocparted=codDoc.split("\"");
                                            String codDocfinal = codDocparted[1];
                                            codigoDoctors+=codDocfinal+",";

                                        }
                                        if (i==3){
                                            String idCitafinal=parted2[1];
                                            idCitas+=idCitafinal+",";

                                        }
                                        if (i==4){
                                            String sintoma=parted2[1];
                                            String[] sintomaparted=sintoma.split("\"");
                                            String sintomafinal = sintomaparted[1];
                                            sintomaS+=sintomafinal+",";

                                        }
                                    }
                            }
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putString("estados", estados);
                            editor.putString("fechas", fechas);
                            editor.putString("codigoDoctors", codigoDoctors);
                            editor.putString("idCitas", idCitas);
                            editor.putString("sintomaS", sintomaS);
                            editor.commit();

                            Intent Mostrarcitas = new Intent(getApplicationContext(), MostrarTodasCitasActivity.class);
                            startActivity(Mostrarcitas);

                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
    );
        requestQueue.add(arrayreq);
    }
}

