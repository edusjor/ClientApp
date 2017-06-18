package com.edus.clientapp.Activities;

import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.edus.clientapp.R;
import com.facebook.login.LoginManager;

import java.util.Date;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private ListView lv1;

    //private String[] Citas = { "Cita pendiente: 03/05/2017 ", "Cita pendiente: 09/05/2017 ", "Cita pendiente: 01/06/2017 ", "Cita pendiente: 17/01/2018 "};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reserv_new_cita) {
/*
            Calendar calendar = new GregorianCalendar();
            Date trialTime = new Date();
            calendar.setTime(trialTime);
            System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));

            Toast toast2 = Toast.makeText(getApplicationContext(),"MILLISECOND: " + calendar.get(Calendar.MILLISECOND), Toast.LENGTH_LONG);
            toast2.show();*/
            Intent reservarCita = new Intent(getApplicationContext(), ReservarCitaActivity.class);
            startActivity(reservarCita);

        } else if (id == R.id.todas_las_citas) {
            Intent citasPendientes = new Intent(getApplicationContext(), GetCitasPendientesActivity.class);
            startActivity(citasPendientes);

        } else if (id == R.id.dirip) {
            Intent DirIp = new Intent(getApplicationContext(), DirIpActivity.class);
            startActivity(DirIp);

        } else if (id == R.id.codigo_QR) {
            Intent intent = new Intent(getApplicationContext(), CodigoQRActivity.class);
            startActivity(intent);


        } /*else if (id == R.id.CitasMorosas) {



        } */ else if (id == R.id.logout) {
            logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void logout() {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }


    private void goLoginScreen(){
        Intent intent = new Intent(this,  LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
