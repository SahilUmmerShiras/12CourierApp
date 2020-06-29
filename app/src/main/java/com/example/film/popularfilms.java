package com.example.film;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class popularfilms extends AppCompatActivity {
    public RecyclerView recyclerView;
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String TAG = "popularfilms";
    public static String CATEGORY ="popular";
    public static String API_KEY ="8056c8422681a7174bcebc9b701ae0b8";
    public customadapter customadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popularfilms);
        recyclerView = findViewById(R.id.recyclerView1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiinterface api = retrofit.create(apiinterface.class);
        Call<Feed> resultsCall = api.tmbd(CATEGORY,API_KEY);
        resultsCall.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                ArrayList<Results>  movies = response.body().getResults();
                if(movies!=null)
                {customadapter = new customadapter(popularfilms.this,movies);
                recyclerView.setLayoutManager(new LinearLayoutManager(popularfilms.this));
                recyclerView.setAdapter(customadapter);}
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();

            }
        });




    }
}
