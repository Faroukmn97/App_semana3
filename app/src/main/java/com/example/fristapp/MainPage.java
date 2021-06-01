package com.example.fristapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        //Localizar los controles
        TextView txtApi = (TextView)findViewById(R.id.txtApi);
        TextView txtSaludo = (TextView)findViewById(R.id.lblSaludo);
        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        txtApi.setText(bundle.getString("Api"));
        //Construimos el mensaje a mostrar
         txtSaludo.setText(bundle.getString("Info"));
    }
}