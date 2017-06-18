package com.edus.clientapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.App.SearchFragment;
import com.edus.clientapp.DetallCitaFinalizadaActivity;
import com.edus.clientapp.DetallCitaPagadaActivity;
import com.edus.clientapp.DetallCitaReservadaActivity;
import com.edus.clientapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.R.attr.fragment;

public class MostrarTodasCitasActivity extends AppCompatActivity {



    private TextView tv1;
    private ListView lv1;

    private String[] Citas = { "cita1 ", "cita2", "cita3"};

    List<String> myList = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_pendientes);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        try {


            String estados = pref.getString("estados", "nulo");
            String fechas = pref.getString("fechas", "nulo");
            String codigoDoctors = pref.getString("codigoDoctors", "nulo");
            String idCitas = pref.getString("idCitas", "nulo");

            final String sintomaS = pref.getString("sintomaS", "nulo");


            final String[] estad = estados.split(",");
            String[] fech = fechas.split(",");
            final String[] codDoc = codigoDoctors.split(",");
            final String[] idcit = idCitas.split(",");
            String[] sintom = sintomaS.split(",");


            String cita = "";

            for (int i = 0; i < estad.length; i++) {
                long fechalong = Long.parseLong(fech[i]);

                Date date = new Date(fechalong);
                Calendar cal = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm ");
                sdf.setCalendar(cal);
                cal.setTime(date);


                String fecha = sdf.format(date);


                cita = "Estado de Cita: " + estad[i] + "\n" +
                        "Fecha: " + fecha + "\n" +
                        "Sintomas: " + sintom[i] + "\n" +
                        "Codigo de doctor: " + codDoc[i] + "\n" +
                        "iD cita: " + idcit[i] + "\n";
                myList.add(cita);

            }


            lv1 = (ListView) findViewById(R.id.listView_Pendientes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
            lv1.setAdapter(adapter);
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    String Estado = estad[posicion];

                    if (Estado.equals("reservada")) { //solo la cancela
                        editor.putString("currendiDcita", idcit[posicion]);
                        editor.commit();
                        Intent DetallCitaReservada = new Intent(getApplicationContext(), DetallCitaReservadaActivity.class);
                        startActivity(DetallCitaReservada);

                    }
                    if (Estado.equals("pagada")) {//dice que ya la pago muestra comentarios
                        editor.putString("currendiDcita", idcit[posicion]);
                        editor.commit();

                        Intent DetallCitaPagada = new Intent(getApplicationContext(), DetallCitaPagadaActivity.class);
                        startActivity(DetallCitaPagada);

                    }


                    if (Estado.equals("cancelada")) {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Esta Cita fue cancelada anteriormente", Toast.LENGTH_LONG);
                        toast2.show();


                    }
                    if (Estado.equals("finalizada")) {//"muestra monto" Permite pagarla boton
                        editor.putString("currendiDcita", idcit[posicion]);
                        editor.putString("currendcodDoc", codDoc[posicion]);
                        editor.commit();

                        Intent DetallCitaFinalizada = new Intent(getApplicationContext(), DetallCitaFinalizadaActivity.class);
                        startActivity(DetallCitaFinalizada);

                    }


                    // tv1.setText("Población de "+ lv1.getItemAtPosition(posicion) + " es "+ habitantes[posicion]);


                }
            });
        }catch (Exception e){
            Log.e("Error","may be wrong ip server");

        }
    }



////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed()
    {
        // Añade más funciones si fuese necesario
        super.onBackPressed();  // Invoca al método
        Intent menu = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(menu);
    }

}