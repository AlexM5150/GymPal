package com.example.gympal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.NotNull;

// This signup class representing the signup page takes in information for new users to be used later.
public class Signup extends AppCompatActivity {

    private Button btnSignMeUp;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText email;
    private EditText phone;
    private Context context;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context = getApplicationContext();
        btnSignMeUp = findViewById(R.id.Next);
        username = findViewById(R.id.signUsername);
        password = findViewById(R.id.signPassword);
        repassword = findViewById(R.id.signRepassword);
        email = findViewById(R.id.signEmail);
        phone = findViewById(R.id.signPhone);
        firebaseAuth = FirebaseAuth.getInstance();
        setTitle("Signup");

        // Sign Me Up Button creation to add to database
        btnSignMeUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString();
                String emailaddress = email.getText().toString().trim();
                int cell = phone.length();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Intent toUserInfo = new Intent(context, com.example.project1.UserInfo.class);


                boolean correct = true;
                // Checks to see if username already exists in database
                if(pass.length() < 6) {
                    Toast.makeText(context, "Password needs to be longer than 6 characters", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Checks to see if repass matches with pass
                if(!pass.equals(repass)) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Checks input email address to see if email box is empty
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(context, "Enter an email address", Toast.LENGTH_SHORT).show();
                    correct = false;
                    // Checks email format and returns invalid if not in correct format
                } else if(!email.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
                // Formats cell number, keeping length to standard phone number length
                if(cell != 10 && cell != 11) {
                    if(cell > 0) {
                        Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(context, "Enter a phone number", Toast.LENGTH_SHORT).show();

                    correct = false;
                }
                // If all checks out, credentials are added into database
                if(correct) {
                    firebaseAuth.createUserWithEmailAndPassword(emailaddress, pass)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username.getText().toString())
                                                .build();
                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "Registration Complete", Toast.LENGTH_LONG).show();
                                                            finish();
                                                            startActivity(toUserInfo);
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                        Toast.makeText(context, "Failed Registration: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}