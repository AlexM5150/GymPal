package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateStats extends AppCompatActivity {
    private Button confirm;
    private Context context;
    private EditText squat;
    private EditText bench;
    private EditText deadlift;

   String squatNum;
   String benchNum;
   String deadliftNum;


    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stats);

        squat = findViewById(R.id.lift1);
        bench = findViewById(R.id.lift2);
        deadlift = findViewById(R.id.lift3);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        context = getApplicationContext();
        confirm = findViewById(R.id.updateConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                squatNum = squat.getText().toString();
                benchNum = bench.getText().toString();
                deadliftNum = deadlift.getText().toString();

                if(TextUtils.isEmpty(squatNum) || TextUtils.isEmpty(benchNum) ||
                        TextUtils.isEmpty(deadliftNum)){
                    Toast.makeText(context, "Please fill all fields."
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    update();

                    Intent done = new Intent(context, Navigation.class);
                    finish();
                    startActivity(done);
                    Toast.makeText(context, "Your Lifting Stats Have been updated!"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void update() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        myRef.child("squatNum").setValue(squatNum);
        myRef.child("benchNum").setValue(benchNum);
        myRef.child("deadliftNum").setValue(deadliftNum);
    }
}