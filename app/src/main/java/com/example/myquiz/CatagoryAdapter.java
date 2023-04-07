package com.example.myquiz;

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

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.viewholder>{
    Context context;
    ArrayList<CatagoryItems>items;

    public CatagoryAdapter(Context context, ArrayList<CatagoryItems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singlerow,null);
         return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
           CatagoryItems catagoryItems=items.get(position);
           holder.textView.setText(catagoryItems.getName());
           Glide.with(context).load(catagoryItems.getImage()).into(holder.imageView);
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(context,QuestionsAnswer.class);
                   intent.putExtra("catagoryid",catagoryItems.getId());
                   context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
       ImageView imageView;
       TextView textView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.catagoryimage);
            textView=itemView.findViewById(R.id.catagoryname);
        }
    }
}
