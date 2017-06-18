package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edus.clientapp.R;

public class ReservarCitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_cita);
        Intent intent = new Intent(this,  BuscarDrActivity.class);
        startActivity(intent);
    }
}
