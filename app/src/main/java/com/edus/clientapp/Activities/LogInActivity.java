package com.edus.clientapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.edus.clientapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {
    String data = "";
    RequestQueue requestQueue;
    LoginButton loginButton;
    CallbackManager callbackManager;
    Profile profile;
    TextView textView;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText textBox;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        textBox = (EditText) findViewById(R.id.editText);
        Button button= (Button) findViewById(R.id.getIpbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strIP = textBox.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("serverIp", strIP);
                editor.commit();
                Toast toast2 = Toast.makeText(getApplicationContext(),"IP Guardada", Toast.LENGTH_SHORT);
                toast2.show();

            }
        });





        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");

        if (AccessToken.getCurrentAccessToken() != null){
            goMenuScreen();
        }

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("tag", "successLogin");
                goMenuScreen();
                try {
                    enviardatos();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onCancel() {
                Log.e("tag", "cancel");
                // App code
                textView.setText("Login cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                //textView.setText("Login Error");
                Log.e("tag", "error");

            }
        });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged (Profile oldProfile, Profile currentProfile) {
                Log.e("tag", "changedProfile");
                //profile = currentProfile;
                try {
                    //updateName();
                    //profile = Profile.getCurrentProfile();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    String idPaciente= "" + currentProfile.getId();
                    String nombre = "" + currentProfile.getFirstName();

                    editor.putString("idPaciente", idPaciente);
                    editor.putString("nombre", nombre);
                    editor.commit();
                    Log.e("ID: ", idPaciente);
                    updateName();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        if (!profileTracker.isTracking()) {
            profileTracker.startTracking();
        }
        try {
            updateName();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateName() throws JSONException {
        profile = Profile.getCurrentProfile();
        String tokenUsuario="";
        if (profile!=null){
            tokenUsuario = AccessToken.getCurrentAccessToken().getToken();

            //Log.e("token",tokenUsuario);
            //enviar();

        }}



    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    class ResponseAsynk extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }









    private void goMenuScreen() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

        ////////////////////////////////////////////////////

    private void enviardatos() throws JSONException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("idPaciente", "291236367992561");
        editor.commit();

        String serverIP = pref.getString("serverIp", "nulo");
        String nombre = pref.getString("nombre", "nulo");
        String idPaciente = pref.getString("idPaciente", "nulo");


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://"+serverIP+"/mavenproject1/webapi/checkIn";

        final JSONObject obj = new JSONObject();
        obj.put("idPaciente",idPaciente);
        obj.put("nombre","Eduardo");
        obj.put("email","solanojorge77@gmail.com");
        Log.e("id edu",idPaciente);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response is: ", response.toString());
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Datos Enviados"+" Respuesta:  "+response.toString(), Toast.LENGTH_LONG);
                        toast1.show();
                        Toast toast2 = Toast.makeText(getApplicationContext(),"Verifique su cuenta si aun no lo ha hecho", Toast.LENGTH_LONG);
                        toast2.show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getCause();
                Log.e("error ","That didn't work!");
                Toast toast2 = Toast.makeText(getApplicationContext(),"Datos no enviados", Toast.LENGTH_LONG);
                toast2.show();
            }
        });
        queue.add(stringRequest);
    }
}






