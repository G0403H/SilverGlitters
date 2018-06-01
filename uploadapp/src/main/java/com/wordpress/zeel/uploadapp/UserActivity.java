package com.wordpress.zeel.uploadapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    UserRecyclerAdapter userRecyclerAdapter;
    RecyclerView recyclerView;
    ProgressBar progressCircle;

    DatabaseReference mDatabaseRef;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        progressCircle = findViewById(R.id.user_progressCircle);
        recyclerView = findViewById(R.id.UserRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));

        userList = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(userList, getApplicationContext());
        recyclerView.setAdapter(userRecyclerAdapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    user.setKey(snapshot.getKey());
                    userList.add(user);
                }

                Collections.sort(userList, new Comp());

                // updates recyclerView every time there is a change in Database
                userRecyclerAdapter.notifyDataSetChanged();
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private class Comp implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            String a = o1.getName();
            String b = o2.getName();

            return a.compareToIgnoreCase(b);
        }
    }

    private class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserRecyclerViewHolder> {

        List<User> users;
        Context context;

        public UserRecyclerAdapter(List<User> users, Context context) {
            this.users = users;
            this.context = context;
        }

        @NonNull
        @Override
        public UserRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false);
            return new UserRecyclerViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final UserRecyclerViewHolder holder, final int position) {
            holder.txtUserName.setText(users.get(position).getName());
            holder.txtUserEmail.setText(users.get(position).getEmail());
            holder.txtUserNumber.setText(users.get(position).getPhone());

            if (users.get(position).isDisabled()) {
                holder.relativeLayout.setBackground(getDrawable(R.color.lightwhite));
                holder.txtUserName.setTextColor(R.color.lightgrey);
                holder.imgDisable.setImageResource(R.drawable.wrong_circle_black_36dp);
            } else {
                holder.imgDisable.setImageResource(R.drawable.right_circle_outline_black_36dp);
            }

            holder.imgDisable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!users.get(position).isDisabled()) {
                        users.get(position).setDisabled(true);
                        holder.relativeLayout.setBackground(getDrawable(R.color.lightwhite));
                        holder.txtUserName.setTextColor(R.color.lightgrey);
                        holder.imgDisable.setImageResource(R.drawable.wrong_circle_black_36dp);

                        Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show();

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(users.get(position).getKey()).setValue(users.get(position), null);
                    } else {
                        users.get(position).setDisabled(false);
                        holder.txtUserName.setTextColor(R.color.Black);
                        holder.relativeLayout.setBackground(getDrawable(R.color.White));
                        holder.imgDisable.setImageResource(R.drawable.right_circle_outline_black_36dp);

                        Toast.makeText(context, "Enabled", Toast.LENGTH_SHORT).show();

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(users.get(position).getKey()).setValue(users.get(position), null);
                    }
                }
            });

            holder.txtUserNumber.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Log.d("phone:",users.get(position).getPhone());
                    intent.setData(Uri.parse("tel:" + users.get(position).getPhone()));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        protected class UserRecyclerViewHolder extends RecyclerView.ViewHolder {

            TextView txtUserName, txtUserEmail, txtUserNumber;
            ImageView imgDisable;
            RelativeLayout relativeLayout;

            public UserRecyclerViewHolder(View itemView) {
                super(itemView);
                txtUserName = itemView.findViewById(R.id.user_name);
                txtUserEmail = itemView.findViewById(R.id.user_email);
                txtUserNumber = itemView.findViewById(R.id.user_number);
                imgDisable = itemView.findViewById(R.id.disableView);
                relativeLayout = itemView.findViewById(R.id.userRelativeLayout);

//                txtUserEmail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                txtUserNumber.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
        }
    }
}
