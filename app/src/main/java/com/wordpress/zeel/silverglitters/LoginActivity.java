package com.wordpress.zeel.silverglitters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email,txt_password;
    Button signinbutton;
    TextView link;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

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
                final String email = txt_email.getText().toString().trim();
                final String password = txt_password.getText().toString().trim();

                if (email.isEmpty()) {
                    txt_email.setError(getString(R.string.input_error_email));
                    txt_email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txt_email.setError(getString(R.string.input_error_email_invalid));
                    txt_email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    txt_password.setError(getString(R.string.input_error_password));
                    txt_password.requestFocus();
                    return;
                }

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (!isConnected){
                    Snackbar.make(view,"No Internet Connection",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Signing in...","Please Wait...",true);
                mDatabase = FirebaseDatabase.getInstance().getReference("users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = null;
                        boolean isdisabled = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            user = snapshot.getValue(User.class);
                            if (user.getEmail().equals(txt_email.getText().toString()) && user.disabled){
                                isdisabled=true;
                                Toast.makeText(LoginActivity.this, "You are blocked by the Admin", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else if (user.getEmail().equals(txt_email.getText().toString())){
                                break;
                            }
                        }
                        progressDialog.dismiss();
                        if (!isdisabled){

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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait...","",true);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            String uid = user.getUid();
            mDatabase= FirebaseDatabase.getInstance().getReference("users").child(uid).child("disabled");
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    if (dataSnapshot!=null){
                        boolean disabled = dataSnapshot.getValue(Boolean.class);
                        if(!disabled){
                            Intent intent = new Intent(LoginActivity.this,Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            mAuth=FirebaseAuth.getInstance();
                            mAuth.signOut();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            progressDialog.dismiss();
        }
    }

}
