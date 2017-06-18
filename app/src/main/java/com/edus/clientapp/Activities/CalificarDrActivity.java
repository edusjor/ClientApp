package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.edus.clientapp.Activities.MenuActivity;
import com.edus.clientapp.R;

public class CalificarDrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_dr);
    }
    public void aceptar(View view){
        //Enviar comentarios y calificacion si existe

        Intent volver = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(volver);

    }

}
