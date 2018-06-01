package com.wordpress.zeel.uploadapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    TextView email_j,emai_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        email_j = findViewById(R.id.email_j);
        emai_h = findViewById(R.id.email_h);

        emai_h.setMovementMethod(LinkMovementMethod.getInstance());
        email_j.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
