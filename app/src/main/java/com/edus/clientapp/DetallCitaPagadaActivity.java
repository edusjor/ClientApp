package com.edus.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.edus.clientapp.Activities.MenuActivity;
import com.edus.clientapp.Activities.MostrarTodasCitasActivity;

public class DetallCitaPagadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_cita_pagada);
        
    }

    public void retroceder(View view){
        Intent atras= new Intent(getApplicationContext(), MostrarTodasCitasActivity.class);
        startActivity(atras);
    }

    public void irAMenu(View view){
        Intent iralmenu= new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(iralmenu);
    }
}
