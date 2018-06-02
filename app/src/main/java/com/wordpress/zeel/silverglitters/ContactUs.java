package com.wordpress.zeel.silverglitters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    TextView txtemail,txtphone,web,txtWhatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        web = findViewById(R.id.web);
        txtemail = findViewById(R.id.email);
        txtphone = findViewById(R.id.phone);
        txtWhatsapp = findViewById(R.id.whatsapp);

        web.setMovementMethod(LinkMovementMethod.getInstance());

        txtemail.setMovementMethod(LinkMovementMethod.getInstance());

        txtphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txtphone.getText().toString()));
                startActivity(intent);
            }
        });

        txtWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + txtWhatsapp.getText().toString());
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
    }
}
