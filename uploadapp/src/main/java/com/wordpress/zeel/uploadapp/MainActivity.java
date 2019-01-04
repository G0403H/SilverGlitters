package com.wordpress.zeel.uploadapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextMail, mEditTextPasscode;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextMail = findViewById(R.id.login_email);
        mEditTextPasscode = findViewById(R.id.passcode);
        mButtonLogin = findViewById(R.id.signinbutton);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSecurely();
            }
        });
    }

    private void loginSecurely() {

        String mailID = mEditTextMail.getText().toString();
        String passcode = mEditTextPasscode.getText().toString();

        if (mailID == "" || passcode == "") {
            Toast.makeText(this, "Fill both the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String passcodeHash = bin2hex(getHash(passcode));

        if (mailID.equals(getString(R.string.nav_header_subtitle))
                && passcodeHash.equals(getString(R.string.passcodeHash))) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid login credentials. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }
}
