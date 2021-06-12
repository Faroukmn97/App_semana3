package com.example.fristapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {

    List<ElementJournalList> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Bundle bundle = this.getIntent().getExtras();
        init(bundle.getString("json"));


    }

    public void init(String json){
        elements = new ArrayList<>();
        int size = json.length();

        try {
            if (size > 0)
            {
                JSONArray Ja = new JSONArray(json);
                for(int i=0; i < Ja.length(); i++)
                {
                    JSONObject jsonObject = new JSONObject(Ja.get(i).toString());
                    elements.add(new ElementJournalList(jsonObject.getString("issue_id"), "Volumen: "+ jsonObject.getString("volume"),
                            jsonObject.getString("number"), "Año: " + jsonObject.getString("year"),
                            "Publicación: "+jsonObject.getString("date_published"),jsonObject.getString("title"),
                            "DOI: "+jsonObject.getString("doi"),jsonObject.getString("cover")));
                }
                ListAdapter listAdapter = new ListAdapter(elements, this);
                RecyclerView recyclerView = findViewById(R.id.ListRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(listAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}