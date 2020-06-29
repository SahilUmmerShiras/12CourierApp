package com.example.film;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class details extends AppCompatActivity {
    TextView textViewabc,textView2,textView3;
    ImageView image;

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static String API_KEY ="8056c8422681a7174bcebc9b701ae0b8";
    customadapter customadapter;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    databasehelper databasehelper = new databasehelper(details.this);
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textViewabc =findViewById(R.id.textViewabc);

        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.textView4);
        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.imageView3);
        String  id = getIntent().getStringExtra("id");


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiinterface api = retrofit.create(apiinterface.class);
        Call<Results> resultcalls = api.related(id,API_KEY);
        resultcalls.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                String Title = response.body().getTitle();
                String Release = response.body().getRelease_date();
                String Image = response.body().getPoster_path();
                String Overview = response.body().getOverview();

                String url = "https://image.tmdb.org/t/p/w500"+Image;
                textViewabc.setText(Title);
                textView2.setText(Release);
                textView3.setText(Overview);
                Glide.with(details.this).load(url).placeholder(R.drawable.ic_texture_black_24dp).into(image);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"NOPE",Toast.LENGTH_SHORT).show();
            }
        });
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit2.create(apiinterface.class);
        Call<Feed> simcall = api.similar(id,API_KEY);
        simcall.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                if(response.body()!=null)
                {
                    ArrayList<Results>  movies = response.body().getResults();
                    customadapter = new customadapter(details.this,movies);
                    recyclerView.setLayoutManager(new LinearLayoutManager(details.this));
                    recyclerView.setAdapter(customadapter);}
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"NOPE",Toast.LENGTH_SHORT).show();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sql = databasehelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("TAGID",id);
                Cursor cursor = sql.rawQuery("SELECT TAGID FROM MOVIES WHERE TAGID =?",new String[] {id});
                if(cursor.getCount()>0)
                {
                    Toast.makeText(getApplicationContext(),"Already exists in WatchList",Toast.LENGTH_SHORT).show();
                }
                else
                {long d = sql.insert("MOVIES",null,values);
                if(d==-1)
                {Toast.makeText(getApplicationContext(),"Did not add to Watch List",Toast.LENGTH_SHORT).show();
            }
            else
                {Toast.makeText(getApplicationContext(),"Added to Watch List",Toast.LENGTH_SHORT).show();
                }
            }}

        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(details.this,WatchList.class);
                startActivity(intent);
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.watchlist: intent = new Intent(details.this,WatchList.class);
                startActivity(intent);
                break;
            case R.id.top_rated: intent = new Intent(details.this,topratedfilms.class);
                startActivity(intent);
                break;
            case R.id.coming_soon: intent = new Intent(details.this,upcomingfilms.class);
                startActivity(intent);
                break;
            case R.id.popular: intent = new Intent(details.this,popularfilms.class);
                startActivity(intent);
                break;
            case R.id.now_showing: intent = new Intent(details.this,nowshowingfilms.class);
                startActivity(intent);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}


