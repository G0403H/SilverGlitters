package com.wordpress.zeel.uploadapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harsh on 12-05-2016.
 */
public class SubRecyclerAdapter extends RecyclerView.Adapter<SubRecyclerAdapter.RecyclerViewHolder>{

    List<Upload> mUploads;
    Context context;
    DatabaseReference mDatabaseRef;

    public SubRecyclerAdapter(List<Upload> mUploads, Context context, DatabaseReference mDatabaseRef) {
        this.mUploads = mUploads;
        this.context = context;
        this.mDatabaseRef = mDatabaseRef;
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
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        ImageView img_image;
        TextView text_title,text_subtitle;
        CardView card;

        public RecyclerViewHolder(View v) {
            super(v);
            img_image = (ImageView)v.findViewById(R.id.card_image);
            text_title = (TextView)v.findViewById(R.id.card_title);
            text_subtitle = (TextView)v.findViewById(R.id.card_subtitle);
            card = (CardView)v.findViewById(R.id.card_view);

            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Bundle bundle = new Bundle();
                bundle.putString("Subcategory_title", mUploads.get(position).getName());
                bundle.putString("Subcategory_imageURL", mUploads.get(position).getImageURL());
                bundle.putDouble("Subcategory_price", mUploads.get(position).getPrice());

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (item.getItemId() == 1){
                    Upload selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();  // Here KEY is Key

                    StorageReference imageRef = FirebaseStorage.getInstance()
                            .getReferenceFromUrl(selectedItem.getImageURL());
                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabaseRef.child(selectedKey).removeValue();
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Delete failed, try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            return true;
        }
    }
}
