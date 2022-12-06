package com.example.gympal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
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
    private Button btnSignup;
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
        btnSignup = findViewById(R.id.Signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        loginlogotext = findViewById(R.id.loginlogotext);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // This keeps the user logged in, checks to see if user is set to null, if not it will
        if(user != null) {
            Intent login = new Intent(context, Navigation.class);
            user = firebaseAuth.getInstance().getCurrentUser();
            String displayName = user.getDisplayName();
            login.putExtra("displayName", displayName);
            finish();
            startActivity(login);
        }

        // Logo Design
        loginlogotext = findViewById(R.id.loginlogotext);
        TextPaint paint = loginlogotext.getPaint();
        float width = paint.measureText(loginlogotext.getText().toString());
        Shader textShader = new LinearGradient(0, 0, width, loginlogotext.getTextSize(),
                new int[]{
                        Color.parseColor("#3DBDB0"),
                        Color.parseColor("#04B5CE"),
                }, null, Shader.TileMode.CLAMP);
        loginlogotext.getPaint().setShader(textShader);
        loginlogotext.setTextColor(Color.parseColor("#3DBDB0"));


        // Login button leads to welcome activity page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validate (email.getText().toString(), password.getText().toString());
            }

        });

        // Sign up button leads to signup activity page
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signUp = new Intent(context, Signup.class);
                finish();
                startActivity(signUp);
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