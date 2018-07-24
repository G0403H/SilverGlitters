package com.wordpress.zeel.silverglitters;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DetailActivity extends AppCompatActivity {

    String categoryName;
    String subCategoryName;
    String subimageURL;
    double subprice;
    User currentUser;

    TextView mTextViewPrice, mTextViewOrder;
    ImageView mToolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewPrice = findViewById(R.id.text_price);
        mTextViewOrder = findViewById(R.id.txt_order);
        mToolbarImage = findViewById(R.id.toolbar_image);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryName = bundle.getString("category_title");
            subCategoryName = bundle.getString("Subcategory_title");
            subimageURL = bundle.getString("Subcategory_imageURL");
            subprice = bundle.getDouble("Subcategory_price");

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(subCategoryName);


            mTextViewPrice.setText("₹" + String.format("%.2f", subprice));
            Picasso.get()
                    .load(subimageURL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .fit()
                    .centerCrop()
                    .into(mToolbarImage);
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

        mTextViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse("smsto:" + R.string.contact_number);
//                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                i.setType("text/plain");
//                i.setPackage("com.whatsapp");
//                i.putExtra(Intent.EXTRA_TEXT, "Hello from Harsh Vasoya!");
//                startActivity(i);
                onClickWhatsApp(mTextViewOrder);
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
