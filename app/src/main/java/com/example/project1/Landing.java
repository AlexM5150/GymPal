package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.Navigation;
import com.example.project1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Landing extends AppCompatActivity {
    private Button btnCreateAcc;
    private Button btnLogin;
    private Button btnGoogleLogin;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private EditText email;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        context = getApplicationContext();
        btnLogin = findViewById(R.id.Landing_login);
        btnCreateAcc = findViewById(R.id.Signup);
        btnGoogleLogin = findViewById(R.id.Google_login); // Google Login Method
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        firebaseAuth = FirebaseAuth.getInstance();
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

        // Login button leads to Login Activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Login = new Intent(context, Login.class);
                finish();
                startActivity(Login);
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup = new Intent(context, Signup.class);
                finish();
                startActivity(Signup);
            }
        });

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() { // Google Login Button
            public void onClick(View v) {
                Intent GoogleLogin = new Intent(context, GoogleSignUp.class);
                finish();
                startActivity(GoogleLogin); // Moves to the Google Sign Up Activity
            }
        });
    }
}
