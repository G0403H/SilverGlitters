package com.wordpress.zeel.silverglitters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String title[] = {"Necklace", "Ring", "Bikini"};
        String subtitle[] = {"...", "---", "Oh yeah"};

        recyclerAdapter = new RecyclerAdapter(title,subtitle,getApplicationContext());
        gridLayoutManager = new GridLayoutManager(null,2);
        layoutManager = gridLayoutManager;

        recyclerView = findViewById(R.id.dashboard_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
