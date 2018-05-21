package com.wordpress.zeel.silverglitters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SubDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SubRecyclerAdapter recyclerAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String title[] = {"Strapless Bikini", "Microkini Bikini", "Sling Bikini"};
        String price[] = {"$25", "$33", "$100"};

        recyclerAdapter = new SubRecyclerAdapter(title, price, this.getApplicationContext());
        gridLayoutManager = new GridLayoutManager(null, 2);
        layoutManager = gridLayoutManager;

        recyclerView = findViewById(R.id.dashboard_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
