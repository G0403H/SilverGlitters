package com.wordpress.zeel.uploadapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    String subCategoryName;
    String subimageURL;
    double subprice;

    TextView mTextViewPrice;
    ImageView mToolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewPrice = findViewById(R.id.text_price);
        mToolbarImage = findViewById(R.id.toolbar_image);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subCategoryName = bundle.getString("Subcategory_title");
            subimageURL = bundle.getString("Subcategory_imageURL");
            subprice = bundle.getDouble("Subcategory_price");

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(subCategoryName);

            mTextViewPrice.setText("Price: " + subprice);
            Picasso.get()
                    .load(subimageURL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .fit()
                    .centerCrop()
                    .into(mToolbarImage);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}
