package com.example.film;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchList extends AppCompatActivity {
    public RecyclerView recyclerView;
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    databasehelper db = new databasehelper(this);
    public FloatingActionButton fab2;

    public static String API_KEY ="8056c8422681a7174bcebc9b701ae0b8";
    public customadapter customadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        recyclerView = findViewById(R.id.recyclerView1);
        SQLiteDatabase sql = db.getReadableDatabase();
        final ArrayList<Results> array = new ArrayList();
        Cursor cursor = sql.query("MOVIES", null, null, null, null, null, null);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiinterface api = retrofit.create(apiinterface.class);
        for (int i = 0; i < cursor.getCount(); i++) {
            if (!cursor.moveToPosition(i)) {
                return;
            }
            String a = cursor.getString(cursor.getColumnIndex("TAGID"));
            Log.d("herebrosee", a);
            Call<Results> resultsCall = api.related(a, API_KEY);
            resultsCall.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    Results movies = response.body();
                    array.add(movies);
                    show(array);
                }


                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

        }
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove((String) viewHolder.itemView.getTag(), array);
                Toast.makeText(getApplicationContext(), "DELETED", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        fab2 = findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(array.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"WATCHLIST IS EMPTY",Toast.LENGTH_SHORT).show();
                }
                else{
                SQLiteDatabase sql = db.getWritableDatabase();
                new AlertDialog.Builder(WatchList.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete all movies? " +
                                "\n Just swipe if you want to delete a single movie.")


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sql.delete("MOVIES", null, null);
                                customadapter.notifyDataSetChanged();
                                array.clear();
                                show(array);
                                Toast.makeText(getApplicationContext(), "YOU NOW HAVE AN EMPTY WATCHLIST", Toast.LENGTH_SHORT).show();
                            }
                        })


                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }}
        });


    }



        void show(ArrayList<Results> array)
        {
            customadapter = new customadapter(WatchList.this, array);
            recyclerView.setLayoutManager(new LinearLayoutManager(WatchList.this));
            recyclerView.setAdapter(customadapter);
        }
        void remove(String id, ArrayList<Results> array)
        {
            SQLiteDatabase sql = db.getWritableDatabase();
            sql.delete("MOVIES","TAGID ="+id,null);
            customadapter.notifyDataSetChanged();
            for(int i =0;i<array.size();i++)
            {
                if(array.get(i).getId()==id)
                {
                    array.remove(array.get(i));
                }
            }
            show(array);

        }


    }


