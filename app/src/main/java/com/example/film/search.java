package com.example.film;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class search extends AppCompatActivity {
    RecyclerView recyclerView;
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String TAG = "search";
    public static String API_KEY ="8056c8422681a7174bcebc9b701ae0b8";
    public customadapter customadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String Query = getIntent().getStringExtra("query");
        Log.d(TAG,"ahhahahah"+Query);


        recyclerView = findViewById(R.id.recyclerViewsearch);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiinterface api = retrofit.create(apiinterface.class);
        Call<Feed> resultcalls = api.QUERY(API_KEY, URLEncoder.encode(Query));
        resultcalls.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                ArrayList<Results>  movies = response.body().getResults();
                if(movies!=null)
                {customadapter = new customadapter(search.this,movies);
                    recyclerView.setLayoutManager(new LinearLayoutManager(search.this));
                    recyclerView.setAdapter(customadapter);}
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                apiinterface api = retrofit.create(apiinterface.class);
                Call<Feed> resultcalls = api.QUERY(API_KEY, URLEncoder.encode(query));
                resultcalls.enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        ArrayList<Results>  movies = response.body().getResults();
                        if(movies!=null)
                        {customadapter = new customadapter(search.this,movies);
                            recyclerView.setLayoutManager(new LinearLayoutManager(search.this));
                            recyclerView.setAdapter(customadapter);}
                        else{
                            Toast.makeText(getApplicationContext(),"MOVIE DOES NOT EXIST",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
