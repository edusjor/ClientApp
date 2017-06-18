package com.edus.clientapp.Activities;

import android.app.Fragment;
import android.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.App.SearchFragment;
import com.edus.clientapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BuscarDrActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    private FragmentManager fragmentManager;





    String data = "";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_dr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(0); // call search fragment.


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }

    private void displayView(final int position) {
        final int posit = position;

        ////////////////////////////////////////////////////////////////
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String serverIP = pref.getString("serverIp", "nulo");

        //Toast toast2 = Toast.makeText(getApplicationContext(),serverIP, Toast.LENGTH_LONG);
        //toast2.show();

        String JsonURL = "http://"+serverIP+"/mavenproject1/webapi/doctores/query?codDoc=";



        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayreq = new JsonArrayRequest(JsonURL,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String idDr = jsonObject.getString("idDoctor");
                                String nombre = jsonObject.getString("nombre");
                                String codDoc = jsonObject.getString("codDoc");
                                data += ("idDoctor: " + idDr + "\n" +
                                        "Nombre: " + nombre + "\n"+
                                        "codDoc: "+ codDoc+"\n");
                                if (i+1 < response.length()){
                                    data+=",";
                                }

                            }

                            //results.setText(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                                /*Toast toast = Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG);
                                toast.show();*/
                        fragment = null;
                        String fragmentTags = "";
                        switch (posit) {
                            case 0:
                                fragment = new SearchFragment(data);
                                break;
                            default:
                                break;
                        }
                        if (fragment != null) {
                            fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTags).commit();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(arrayreq);



////////////////////////////////////////////////////////////////

    }

}