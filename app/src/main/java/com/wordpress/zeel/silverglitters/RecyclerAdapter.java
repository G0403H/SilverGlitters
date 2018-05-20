package com.wordpress.zeel.silverglitters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Harsh on 12-05-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    String[] title_names,note_names;
    Context context;
    boolean singleLine;

    public  RecyclerAdapter(String[] title, String[] notes, Context c, boolean value){
        this.title_names=title;
        this.note_names=notes;
        this.context=c;
        this.singleLine=value;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_card,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.text_title.setText(title_names[position]);
        holder.text_subtitle.setText(note_names[position]);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title",title_names[position]);
//                bundle.putString("note",note_names[position]);
//                Intent i = new Intent(context, Opennote.class);
//                i.putExtras(bundle);
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title_names.length;
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
