package com.wordpress.zeel.silverglitters;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class SubDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SubRecyclerAdapter recyclerAdapter;
    GridLayoutManager gridLayoutManager;

    String categoryName;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
