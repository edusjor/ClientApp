package com.edus.clientapp.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edus.clientapp.R;

public class DirIpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_ip);

        final EditText textBox;

        textBox = (EditText) findViewById(R.id.editText);
        Button button= (Button) findViewById(R.id.getIpbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strIP = textBox.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("serverIp", strIP);
                editor.commit();
                Toast toast2 = Toast.makeText(getApplicationContext(),"IPguardada", Toast.LENGTH_SHORT);
                toast2.show();

            }
        });
    }
}
