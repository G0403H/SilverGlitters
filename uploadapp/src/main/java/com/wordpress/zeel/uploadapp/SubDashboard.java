package com.wordpress.zeel.uploadapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    SubRecyclerAdapter recyclerAdapter;
    ProgressBar mProgressCircle;

    DatabaseReference mDatabaseRef;
    ValueEventListener mDBListener;

    List<Upload> mUploads;


    java.lang.String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdashboard);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ActionBar actionBar = getSupportActionBar();
            categoryName = bundle.getString("Category_title");

            actionBar.setTitle(categoryName);
        }

        mProgressCircle = findViewById(R.id.progress_circle2);

        recyclerView = findViewById(R.id.dashboard_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(null, 2));
        recyclerView.setHasFixedSize(true);

        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(categoryName);

        recyclerAdapter = new SubRecyclerAdapter(mUploads, this.getApplicationContext(), mDatabaseRef);
        recyclerView.setAdapter(recyclerAdapter);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Upload upload = snapshot.getValue(Upload.class);
                    upload.setKey(snapshot.getKey());
                    mUploads.add(upload);
                }

                // updates recyclerView every time there is a change in Database
                recyclerAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SubDashboard.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
