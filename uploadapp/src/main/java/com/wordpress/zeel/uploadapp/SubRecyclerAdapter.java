package com.wordpress.zeel.uploadapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harsh on 12-05-2016.
 */
public class SubRecyclerAdapter extends RecyclerView.Adapter<SubRecyclerAdapter.RecyclerViewHolder>{

    List<Upload> mUploads;
    Context context;

    public SubRecyclerAdapter(List<Upload> mUploads, Context context) {
        this.mUploads = mUploads;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_card,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.text_title.setText(mUploads.get(position).getName());
        holder.text_subtitle.setText(String.valueOf(mUploads.get(position).getPrice()));
        Picasso.get()
                .load(mUploads.get(position).getImageURL())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.img_image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Subcategory_title", mUploads.get(position).getName());
                bundle.putString("Subcategory_imageURL", mUploads.get(position).getImageURL());
                bundle.putDouble("Subcategory_price", mUploads.get(position).getPrice());

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView img_image;
        TextView text_title,text_subtitle;
        CardView card;

        public RecyclerViewHolder(View v) {
            super(v);
            img_image = (ImageView)v.findViewById(R.id.card_image);
            text_title = (TextView)v.findViewById(R.id.card_title);
            text_subtitle = (TextView)v.findViewById(R.id.card_subtitle);
            card = (CardView)v.findViewById(R.id.card_view);

        }
    }
}
