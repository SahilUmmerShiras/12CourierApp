package com.example.film;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public EditText textView;
   // public String Query;
    public Random random = new Random();
   // public ImageView search;
    public static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView searchbar = findViewById(R.id.searchbar);
        Button image = findViewById(R.id.searchbutton);


        searchbar.setHint("What are you looking for?");

        searchbar.setImeActionLabel("SEARCH", KeyEvent.KEYCODE_ENTER);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchbar.getText().toString();
                if(query.trim().length()==0)
                {
                    return;
                }
                else{
                Intent intent = new Intent(MainActivity.this,search.class);
                intent.putExtra("query",query);
                startActivity(intent);
                searchbar.setText("");
            }}
        });

        searchbar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && searchbar.getText().toString().trim().length()!=0) {
                    String query = searchbar.getText().toString();

                    Intent intent = new Intent(MainActivity.this,search.class);
                    intent.putExtra("query",query);
                    startActivity(intent);
                    searchbar.setText("");
                    return true;
                }
                return false;
            }
        });



        ImageView imageView = findViewById(R.id.imageView2);
        TextView texter1 = findViewById(R.id.textView);
        TextView texter2 = findViewById(R.id.textView2);
        String url=" ";
        String text1=" ",text2=" ";
        //setting background image
        int newnum = random.nextInt(6);
        switch(newnum)
        {
            case 0:  url = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg";
                text1= "Say What Again?";
                text2= "~ Pulp Fiction, Quinten Tarantino";


            break;
            case 1:  url = "https://image.tmdb.org/t/p/w500//bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg";
                text1= "Rule 1: You don't talk about Fight Club, Rule 2: There are no Rules.";
                text2= "~ Fight Club, David Fincher";
                break;
            case 2:  url = "https://image.tmdb.org/t/p/w500/d4J7GotCjvDJBAYayZBTc5nLbbP.jpg";
                text1= "Well, That's..just..your..opinion man.";
                text2= "~ The Big Lebowski, Coen Brothers";

                break;
            case 3:  url = "https://image.tmdb.org/t/p/w500/6XN1vxHc7kUSqNWtaQKN45J5x2v.jpg";
                text1= "This is how I WIN.";
                text2= "~ Uncut Gems, Safdie Brothers";

                break;
            case 4:  url = "https://image.tmdb.org/t/p/w500/c8Ass7acuOe4za6DhSattE359gr.jpg";
                text1= "Whoever Saves One Life, Save The World Entire.";
                text2= "~ Schindler's List, Steven Spielberg";
                break;
            case 5:  url = "https://image.tmdb.org/t/p/w500/a58oc5qGNazb3QOxEH8eG5S7gjr.jpg";
                text1= "You looking at me?";
                text2= "~ Taxi Driver, Martin Scorcese";

                break;
        }
        Glide.with(this).load(url).centerCrop().placeholder(R.drawable.darjeeling).into(imageView);
        texter1.setText(text1);
        texter2.setText(text2);
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
            case R.id.watchlist: intent = new Intent(MainActivity.this,WatchList.class);
                startActivity(intent);
                break;
            case R.id.top_rated: intent = new Intent(MainActivity.this,topratedfilms.class);
                startActivity(intent);
                break;
            case R.id.coming_soon: intent = new Intent(MainActivity.this,upcomingfilms.class);
                startActivity(intent);
                break;
            case R.id.popular: intent = new Intent(MainActivity.this,popularfilms.class);
                startActivity(intent);
                break;
            case R.id.now_showing: intent = new Intent(MainActivity.this,nowshowingfilms.class);
                startActivity(intent);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
