package com.edus.clientapp.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.edus.clientapp.R;

import java.util.Date;
import java.util.SimpleTimeZone;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CitasDisponDelDrActivity extends AppCompatActivity {
    String DrDetails;
    private ListView lv1;

    private String[] citas =  { "06/05/2017 07:00", "07/05/2017 08:00", "08/05/2017 07:00", "09/05/2017 13:00",
            "10/05/2017 13:00", "11/05/2017 10:00", "13/05/2017 15:30", "13/05/2017 18:00", "14/05/2017 07:00", "15/05/2017 08:00","16/05/2017 08:00" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_dispon_del_dr);





        lv1 =(ListView)findViewById(R.id.listViewCitasdeDr);
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, citas);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Selecciono: "+lv1.getItemAtPosition(posicion), Toast.LENGTH_SHORT);
                toast1.show();
                String FechaHora =" "+lv1.getItemAtPosition(posicion);




                Intent previousIntent = getIntent();
                if (previousIntent != null) {
                    if (previousIntent.hasExtra(Intent.EXTRA_TEXT)) {

                        DrDetails = getIntent().getExtras().getString("DrDetails");
                    }
                }






//*************************************** DATE ****************************************
      /*          // get the supported ids for GMT-08:00 (Pacific Standard Time)
                String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
                // if no ids were returned, something is wrong. get out.
                if (ids.length == 0)
                    System.exit(0);

                // begin output
                System.out.println("Current Time");

                // create a Pacific Standard Time time zone
                SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

                // set up rules for Daylight Saving Time
                pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
                pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

                // create a GregorianCalendar with the Pacific Daylight time zone
                // and the current date and time
                Calendar calendar = new GregorianCalendar(pdt);
                Date trialTime = new Date();
                calendar.setTime(trialTime);


                String YEAR = ""+ calendar.get(Calendar.YEAR);
                String MONTH = ""+ calendar.get(Calendar.MONTH);
                String DAY_OF_MONTH = ""+ calendar.get(Calendar.DAY_OF_MONTH);

*/
//*************************************************************************************

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("FechaHora", FechaHora);
                editor.commit();


                Intent intent = new Intent(getApplicationContext(), GrabarSintomasActivity.class);
                startActivity(intent);

            }


        });
    }


}



