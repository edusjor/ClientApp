package com.edus.clientapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.edus.clientapp.R;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import android.content.Intent;
import android.net.Uri;

public class CodigoQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView escanerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);
        EscanerQR ();
    }

    public void  EscanerQR (){
        escanerView = new ZXingScannerView(this);
        setContentView(escanerView);
        escanerView.setResultHandler(this);
        escanerView.startCamera();

    }

    //Hacer algo con el resultado
    @Override
    public void handleResult(Result result){
        try {
            escanerView.stopCamera();
            openlink(result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openlink(String link) throws Exception{
        Intent intent = null;
        intent = new Intent(intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);

    }
}