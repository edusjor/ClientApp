package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edus.clientapp.R;


public class DetallCitaPendActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_cita_pend);


    }
    public void Aceptar(View view){
        Intent DetallCita = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(DetallCita);

    }

    public void CancelarCita(View view){
        Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Cita Cancelada", Toast.LENGTH_SHORT);
        toast1.show();

        Intent volver = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(volver);
    }
}
