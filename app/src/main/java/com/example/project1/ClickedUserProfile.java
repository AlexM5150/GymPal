package com.example.project1;


import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class ClickedUserProfile extends AppCompatActivity {
    private TextView height;
    private TextView activityLevel;
    private TextView profileName;
    private TextView gender;
    private TextView style;
    private TextView location;
    private Toolbar toolbar;
    private ImageView profilepic;
    private TextView bio;

    private FirebaseDatabase userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the user object from the bundle
        Bundle bundle = getIntent().getExtras();
        UserProfile userProfile = (UserProfile) bundle.getSerializable("clickedUser");

        profileName = findViewById(R.id.profile_name);
        gender = findViewById(R.id.profile_gender);
        height = findViewById(R.id.profile_height);
        activityLevel = findViewById(R.id.profile_activity_level);
        toolbar = findViewById(R.id.profile_toolbar);
        style = findViewById(R.id.profile_style);
        profilepic = findViewById(R.id.profilePage_pic);
        location = findViewById(R.id.profile_location);
        bio = findViewById(R.id.profile_bio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userData = FirebaseDatabase.getInstance();

        profileName.setText(userProfile.getDisplayName());
        gender.setText(userProfile.getGender());
        height.setText(userProfile.getFeetNum() + "'" + userProfile.getInchNum());
        activityLevel.setText(userProfile.getaLevel());
        location.setText(userProfile.getLocation());
        style.setText(userProfile.getStyle());
        profilepic.setImageURI(Uri.parse(userProfile.getProfilePic()));
        bio.setText(userProfile.getBio());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }
}
