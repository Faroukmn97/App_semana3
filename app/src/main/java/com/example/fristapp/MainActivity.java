package com.example.fristapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fristapp.interfaces.JournalAPI_Retrofit;
import com.example.fristapp.models.Journal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre;
    String Info="";
    TextView contexto;
    Button btnBuscar;
    Button btnBuscar2;
    RequestQueue requestQueue;
    private static final String URL="https://revistas.uteq.edu.ec/ws/issues.php?j_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         txtNombre =  (EditText)findViewById(R.id.txtNombre);

        btnBuscar = findViewById(R.id.btn_enviar1);
        btnBuscar2 = findViewById(R.id.btn_enviar2);

        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                find(txtNombre.getText().toString());

            }
        });

        btnBuscar2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                find_volley_JSON(txtNombre.getText().toString());

            }
        });
    }


    private void find(String codigo){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://revistas.uteq.edu.ec/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        JournalAPI_Retrofit journalAPI = retrofit.create(JournalAPI_Retrofit.class);
        Call<List<Journal>> call = journalAPI.find(codigo);
        call.enqueue(new Callback<List<Journal>>() {
            @Override
            public void onResponse(Call<List<Journal>> call, Response<List<Journal>> response) {
                List<Journal> lj = response.body();
                Info = "";
                 for(Journal str : lj )
                {
                    Info+=  "API Retrofit" + "\n\n\n"+
                            "ID:"+str.getIssue_id()+" |-| "+
                            "Volumen:"+str.getVolume()+" |-| "+
                            "Número:"+str.getNumber()+" |-| "+
                            "Año:"+str.getYear()+" |-| "+
                            "Fecha de publicación:"+str.getDate_published()+" |-| "+
                            "Titulo:"+str.getTitle()+" |-| "+
                            "DOI:"+str.getDoi()+" |-| "+
                            "Portada:"+str.getCover()+"\n\n";
                   // contexto.append(Info);
                }
                Intent intent = new Intent(MainActivity.this, MainPage.class);

                //Creamos la información a pasar entre actividades - Pares Key-Value
                Bundle b = new Bundle();
                b.putString("Api", "API Retrofit");
                b.putString("Info", Info);
                //Añadimos la información al intent
                intent.putExtras(b);
                // Iniciamos la nueva actividad
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Journal>> call, Throwable t) {

            }
        });
    }

    private void find_volley_JSON(String codigo){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL + codigo,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        Info = "";
                        try {
                            JSONArray Ja = new JSONArray(response);
                            for(int i=0; i < Ja.length(); i++)
                            {
                                JSONObject jsonObject = new JSONObject(Ja.get(i).toString());
                                Info+=  "ID:"+jsonObject.getString("issue_id")+" |-| "+
                                        "Volumen:"+jsonObject.getString("volume")+" |-| "+
                                        "Número:"+jsonObject.getString("number")+" |-| "+
                                        "Año:"+jsonObject.getString("year")+" |-| "+
                                        "Fecha de publicación:"+jsonObject.getString("date_published")+" |-| "+
                                        "Titulo:"+jsonObject.getString("title")+" |-| "+
                                        "DOI:"+jsonObject.getString("doi")+" |-| "+
                                        "Portada:"+jsonObject.getString("cover")+"\n\n";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MainActivity.this, MainPage.class);
                        Bundle b = new Bundle();
                        b.putString("Api", "API Volley");
                        b.putString("Info", Info);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }
}