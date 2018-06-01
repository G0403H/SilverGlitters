package com.wordpress.zeel.uploadapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh on 12-05-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    List<Upload> mUploads;
    Context context;
    DatabaseReference mDatabaseRef;

    public RecyclerAdapter(List<Upload> mUploads, Context context, DatabaseReference mDatabaseRef) {
        this.mUploads = mUploads;
        this.context = context;
        this.mDatabaseRef = mDatabaseRef;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_card_2, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        final Upload currentUpload = mUploads.get(position);
        holder.text_title.setText(currentUpload.getCategory());
        Picasso.get()
                .load(currentUpload.getImageURL())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.img_image);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        ImageView img_image;
        TextView text_title;
        CardView card;

        public RecyclerViewHolder(View v) {
            super(v);
            img_image = (ImageView)v.findViewById(R.id.card_image);
            text_title = (TextView)v.findViewById(R.id.card_title);
            card = (CardView)v.findViewById(R.id.card_view);

            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Bundle bundle = new Bundle();
                bundle.putString("Category_title",mUploads.get(position).getCategory());

                Intent i = new Intent(context, SubDashboard.class);
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
        public boolean onMenuItemClick(final MenuItem item) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (item.getItemId() == 1){
                    Upload selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getCategory();  // Here KEY is category name

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(selectedKey).child(uploadId).setValue(selectedItem);

                    final ArrayList<String> URLs = new ArrayList<>();
                    FirebaseDatabase.getInstance().getReference().child(selectedKey).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    long cnt = dataSnapshot.getChildrenCount();

                                    for (DataSnapshot childsnapshot : dataSnapshot.getChildren() ){

                                        if (cnt==1)
                                            break;

                                        Upload item = childsnapshot.getValue(Upload.class);
                                        URLs.add(item.getImageURL());
                                        cnt -= 1;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            }
                    );

                    for (int i=0;i<URLs.size();i++) {
                        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(URLs.get(i));
                        imageRef.delete();
                    }

                    mDatabaseRef.child(selectedKey).removeValue();
                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
    }
}
