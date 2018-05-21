package com.wordpress.zeel.silverglitters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Harsh on 12-05-2016.
 */
public class SubRecyclerAdapter extends RecyclerView.Adapter<SubRecyclerAdapter.RecyclerViewHolder>{

    String[] title_names, price_names;
    Context context;

    public SubRecyclerAdapter(String[] title, String[] notes, Context c){
        this.title_names=title;
        this.price_names =notes;
        this.context=c;
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
        holder.text_subtitle.setText(price_names[position]);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title",title_names[position]);
//                bundle.putString("note",subtitle_names[position]);
                Intent i = new Intent(context, DetailActivity.class);
//                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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
