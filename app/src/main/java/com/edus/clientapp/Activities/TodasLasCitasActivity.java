package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edus.clientapp.R;

public class TodasLasCitasActivity extends AppCompatActivity {


    private TextView tv1;
    private ListView lv1;

    private String[] Citas = { "Cita: 03/05/2017 ", "Chile", "Paraguay", "Bolivia","cr","nica",
            "Peru", "Ecuador", "Brasil", "Colombia", "Venezuela", "Uruguay" };

    private String[] habitantes = { "40000000", "17000000", "6500000",
            "10000000", "30000000", "14000000", "183000000", "44000000",
            "29000000", "3500000" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_las_citas);



        lv1 =(ListView)findViewById(R.id.listView_Pendientes);
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Citas);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                //tv1 = (TextView)findViewById(R.id.tv2);
                Intent DetallCita = new Intent(getApplicationContext(), DetallCitaPendActivity.class);
                startActivity(DetallCita);
                /*public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                tv1.setText("Población de "+ lv1.getItemAtPosition(posicion) + " es "+ habitantes[posicion]);*/
            }


        });
    }
}