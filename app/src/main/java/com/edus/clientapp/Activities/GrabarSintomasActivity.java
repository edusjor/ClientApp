package com.edus.clientapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.R;
import com.edus.clientapp.SeleccionarFechaActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GrabarSintomasActivity extends AppCompatActivity   {

    ListView options;
    ArrayList<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_sintomas);
        options = (ListView) findViewById(R.id.lvOptions);
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mencione sus sintomas de manera clara");

        startActivityForResult(i, 1010);


            options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                String sintomas = (String) parent.getAdapter().getItem(posicion);

                editor.putString("sintomas",sintomas );
                editor.commit();
                nextScreen();


            }
        });

        // retrieves data from the previous state. This is incase the phones
        // orientation changes
        if (savedInstanceState != null) {
            results = savedInstanceState.getStringArrayList("results");

            if (results != null)
                options.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, results));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // retrieves data from the VoiceRecognizer
        if (requestCode == 1010 && resultCode == RESULT_OK) {
            results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            options.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, results));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void nextScreen(){
        Intent intent = new Intent(getApplicationContext(), SeleccionarFechaActivity.class);
        startActivity(intent);
    }
}
