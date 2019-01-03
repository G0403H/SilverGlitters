package com.wordpress.zeel.silverglitters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    String categoryName;
    String subCategoryName;
    String subimageURL;
    String mDescription;
    String mWeblink;
    double subprice;
    User currentUser;

    TextView mTextViewPrice, mTextViewWhatsapp, mTextViewDescription, mTextViewWebsite;
    ImageView mToolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewPrice = findViewById(R.id.text_price);
        mTextViewWhatsapp = findViewById(R.id.txt_order);
        //mToolbarImage = findViewById(R.id.toolbar_image);
        mTextViewDescription = findViewById(R.id.text_description);
        mTextViewWebsite = findViewById(R.id.txt_order_website);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryName = bundle.getString("category_title");
            subCategoryName = bundle.getString("Subcategory_title");
            subimageURL = bundle.getString("Subcategory_imageURL");
            subprice = bundle.getDouble("Subcategory_price");
            mDescription = bundle.getString("Subcategory_description");
            mWeblink = bundle.getString("Subcategory_weblink");
            ArrayList<String> list = bundle.getStringArrayList("Subcategory_urlList");
            list.add(0, subimageURL);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(subCategoryName);

            String[] imageUrls = new String[list.size()];
            imageUrls = list.toArray(imageUrls);

            ViewPager viewPager = findViewById(R.id.viewPager);
            ViewPageAdapter adapter = new ViewPageAdapter(this,imageUrls);
            viewPager.setAdapter(adapter);

            CirclePageIndicator circlePageIndicator = findViewById(R.id.indicator);
            circlePageIndicator.setViewPager(viewPager);
            mTextViewPrice.setText("₹" + String.format("%.2f", subprice));
            mTextViewDescription.setText(mDescription);
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currentUser = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        mTextViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickWhatsApp(mTextViewWhatsapp);
            }
        });

        mTextViewWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWeblink));
                startActivity(intent);
            }
        });
    }

    public void onClickWhatsApp(View view) {

        String text = "New order request:\n\n" +
                "Customer name: " + currentUser.getName() +
                "\n\nOrder details:" +
                "\nItem name - " + subCategoryName +
                "\nCategory - " + categoryName +
                "\nPrice - ₹" + String.format("%.2f", subprice) +
                "\n\nAddress: ";

        try {
            text = URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlstring = "https://api.whatsapp.com/send?phone=918000100804&text=" + text;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(urlstring));
        startActivity(i);
    }
}
