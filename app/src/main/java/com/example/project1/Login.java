package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


import org.jetbrains.annotations.NotNull;

// This class represents the login page, which takes user and password and validates user input
// to log in.

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private ProgressDialog progressDialog;
    private TextView loginlogotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        btnLogin = findViewById(R.id.Login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Login button leads to welcome activity page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate (email.getText().toString(), password.getText().toString());
            }

        });

    }

    // Validation method will validate the username and password with the firebase authentication
    // database
    private void validate(String userName, String pass) {

        progressDialog.setMessage("Logging in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()) {
                    Intent login = new Intent(context, Navigation.class);
                    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                    String displayName = user.getDisplayName();
                    login.putExtra("displayName", displayName);
                    finish();
                    startActivity(login);
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}