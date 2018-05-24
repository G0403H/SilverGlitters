package com.wordpress.zeel.silverglitters;


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
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    List<Upload> mUploads;
    Context context;

    public RecyclerAdapter(List<Upload> uploads, Context c) {
        mUploads = uploads;
        this.context = c;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Upload currentUpload = mUploads.get(position);

        holder.text_title.setText(currentUpload.getCategory());
        Picasso.get()
                .load(currentUpload.getImageURL())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.img_image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Category_title", currentUpload.getCategory());

                Intent i = new Intent(context, SubDashboard.class);
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
        TextView text_title;
        CardView card;

        public RecyclerViewHolder(View v) {
            super(v);
            img_image = (ImageView)v.findViewById(R.id.card_image);
            text_title = (TextView)v.findViewById(R.id.card_title);
            card = (CardView)v.findViewById(R.id.card_view);
        }
    }
}
