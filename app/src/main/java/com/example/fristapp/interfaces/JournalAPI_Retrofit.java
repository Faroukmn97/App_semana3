package com.example.fristapp.interfaces;

import com.example.fristapp.models.Journal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface JournalAPI_Retrofit {
    @GET("ws/issues.php")
    public Call<List<Journal>> find(@Query("j_id") String id);
}


