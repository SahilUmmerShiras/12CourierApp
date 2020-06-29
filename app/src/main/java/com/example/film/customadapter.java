package com.example.film;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class customadapter extends RecyclerView.Adapter<customadapter.MyViewHolder> {
    private Context mcontext;
    private ArrayList<Results> mresults;



    public customadapter(Context context, ArrayList<Results> results)
    {
        mcontext = context;
        mresults = results;

    }


    @NonNull
    @Override
    public customadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.mrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter.MyViewHolder holder, final int position) {
        if(mresults==null)
        {
            return;
        }
       holder.tvName.setText(mresults.get(position).getTitle());
        holder.tvOver.setText(mresults.get(position).getOverview());
        holder.tvRelease.setText(mresults.get(position).getRelease_date());
        String url ="https://image.tmdb.org/t/p/w500"+mresults.get(position).getPoster_path();
        Glide.with(mcontext).load(url).fitCenter().placeholder(R.drawable.ic_texture_black_24dp).into(holder.image);
        holder.itemView.setTag(mresults.get(position).getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,details.class);
                intent.putExtra("id", holder.itemView.getTag().toString());
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mresults.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvOver, tvRelease;
        public ImageView image;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvname);
            tvOver = itemView.findViewById(R.id.tvoverview);
            tvRelease = itemView.findViewById(R.id.tvrelease);
            image = itemView.findViewById(R.id.searchclick);

        }
    }}