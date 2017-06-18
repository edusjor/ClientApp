package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.edus.clientapp.R;

public class CitasConMorosidadActivity extends AppCompatActivity {

    private ListView lv1;

    private String[] Citas = { "Cita morosa: 03/05/2017 ", "Cita morosa: 09/05/2017 ", "Cita morosa: 01/06/2017 ", "Cita morosa: 17/01/2018 "};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_con_morosidad);




        //List view
        //----------------------
        lv1 =(ListView)findViewById(R.id.listView_Morosas);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Citas);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {


                Intent DetallCita = new Intent(getApplicationContext(), DetallCitaconMorosidadActivity.class);
                startActivity(DetallCita);
            }


        });
        //-----------------------
    }
}
