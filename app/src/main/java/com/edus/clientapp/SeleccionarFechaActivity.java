package com.edus.clientapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeleccionarFechaActivity extends AppCompatActivity {



    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    private TextView text;
    private Button btn_date;
    private Button btn_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_fecha);


        /////////////////////////////////////
        text = (TextView) findViewById(R.id.txt_TextDateTime);
        btn_date = (Button) findViewById(R.id.btn_datePicker);
        btn_time = (Button) findViewById(R.id.btn_timePicker);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

        updateTextLabel();
        ////////////////////////////////////////////////////


    }
    public void aceptarcita(View view){
        fechatolong();
    }








    public void fechatolong(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();



        String idDr = pref.getString("idDr", "nulo");
        String idPaciente = pref.getString("idPaciente", "nulo");
        String sintomas = pref.getString("sintomas", "nulo");

        String year = pref.getString("year", "nulo");
        String mes = pref.getString("mes", "nulo");
        String dia = pref.getString("dia", "nulo");
        String hora = pref.getString("hora", "nulo");
        String minuto = pref.getString("minuto", "nulo");


        String fecha = year+","+mes+","+dia+" "+hora+","+minuto; //   "2017,04,18 08,0";


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd HH,mm");
            Date date = sdf.parse(fecha);
            long startDate = date.getTime();

            sendDetallesVolley(sintomas,idDr,idPaciente,startDate);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nextScreen();
    }


    //////////////////////////////////////////////////////////

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editor.putString("year", String.valueOf(year));
            editor.putString("mes", String.valueOf(monthOfYear+1));
            editor.putString("dia", String.valueOf(dayOfMonth));
            editor.commit();
            updateTextLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            editor.putString("hora", String.valueOf(hourOfDay));
            editor.putString("minuto", String.valueOf(minute));
            editor.commit();
            updateTextLabel();
        }
    };

    private void updateTextLabel(){
        text.setText(formatDateTime.format(dateTime.getTime()));
    }
    ///////////////////////////////////////////////////////////////////////




    public void nextScreen(){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }


    private void sendDetallesVolley(String sintomas,String idDr, String idPaciente, Long fecha) throws JSONException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String serverIP = pref.getString("serverIp", "nulo");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverIP+"/mavenproject1/webapi/crearCita";

        final JSONObject obj = new JSONObject();
        obj.put("sintomas",sintomas);
        obj.put("idDoctor",idDr);
        obj.put("idPaciente",idPaciente);
        obj.put("fecha",fecha);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.e("Response is: ", response.toString());
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Cita Creada: "+response.toString(), Toast.LENGTH_LONG);
                        toast1.show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getCause();
                Log.e("error ","That didn't work!");
                Toast toast2 = Toast.makeText(getApplicationContext(),"Error al crear cita", Toast.LENGTH_LONG);
                toast2.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}



