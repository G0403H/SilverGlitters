package com.wordpress.zeel.silverglitters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email,txt_password;
    Button signinbutton;
    TextView link;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        link = findViewById(R.id.txt_signup_link);
        txt_email = findViewById(R.id.login_email);
        txt_password = findViewById(R.id.password);
        signinbutton = findViewById(R.id.signinbutton);
        mAuth = FirebaseAuth.getInstance();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Signing in...","Please Wait...",true);

                (mAuth.signInWithEmailAndPassword(txt_email.getText().toString(),txt_password.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user!=null){
//            Intent intent = new Intent(LoginActivity.this,Dashboard.class);
//            startActivity(intent);
//            finish();
//        }
//    }

}
