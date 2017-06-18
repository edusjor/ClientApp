package com.edus.clientapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edus.clientapp.R;

public class DetallCitaconMorosidadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_cita_con_morosidad);



        //Mostrar detalles de cita y precio
        //
    }







    public void nextScreenMenu(View view){
        Intent volver = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(volver);
    }

    public void realizarpago(View view){
        try{
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "Pago Realizado", Toast.LENGTH_SHORT);
            toast1.show();

            Intent Calificar = new Intent(getApplicationContext(), CalificarDrActivity.class);
            startActivity(Calificar);

        }catch (Exception e){
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "No se pudo pagar", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }
}
