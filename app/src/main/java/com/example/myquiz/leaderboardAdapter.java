package com.example.myquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class leaderboardAdapter extends RecyclerView.Adapter<leaderboardAdapter.viewholder>{
    Context context;
    ArrayList<leaderboardItems>items;

    public leaderboardAdapter(Context context, ArrayList<leaderboardItems> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerowitem,null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
          leaderboardItems obj=items.get(position);
          holder.coins.setText(""+obj.getCoins());
          holder.username.setText(obj.getUsername());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public class viewholder extends RecyclerView.ViewHolder
    {
        TextView username,coins;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.userleadername);
            coins=itemView.findViewById(R.id.coins);
        }
    }
}
