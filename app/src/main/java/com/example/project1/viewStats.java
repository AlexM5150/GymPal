package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class viewStats extends AppCompatActivity {
    private TextView squat;
    private TextView bench;
    private TextView deadlift;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;
    private Toolbar toolbar;
    private ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        squat = findViewById(R.id.squat_num);
        bench = findViewById(R.id.bench_num);
        deadlift = findViewById(R.id.deadlift_num);
        toolbar = findViewById(R.id.stats_toolbar);
        profilepic = findViewById(R.id.stats_pic);
        setSupportActionBar(toolbar);


        //squat.setText(updateStats.getSquatNum()+ " lbs");
        //bench.setText(updateStats.getBenchNum() + " lbs");
        //deadlift.setText(updateStats.getDeadliftNum() + " lbs");

        firebaseAuth = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance();

        //Accessing the realtime database, all values are grabbed here in order to set text values
        DatabaseReference databaseReference = userData.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                squat.setText(userProfile.getSquatNum() + " lbs");
                bench.setText(userProfile.getBenchNum() + " lbs");
                deadlift.setText(userProfile.getDeadliftNum() + " lbs");


            }

            // Error message for cancelled
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(viewStats.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}