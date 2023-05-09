package com.example.project1.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project1.R;
import com.example.project1.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class HomeFragment extends Fragment {

    private ImageView profilePicSwipe;
    private Button btnMatch;
    private Button btnSkip;
    private TextView displayNameSwipe;
    private String prevProfile;
    private List<String> skipList = new ArrayList<>();
    private List<String> shownProfile = new ArrayList<>();

    boolean skip;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        profilePicSwipe = view.findViewById(R.id.ProfilePicSwipe);
        displayNameSwipe = view.findViewById(R.id.displayNameSwipe);
        btnMatch = view.findViewById(R.id.yes);
        btnSkip = view.findViewById(R.id.skip);

        skip = false;
        loadRandomProfile();

        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip = false;
                loadRandomProfile();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip = true;
                loadRandomProfile();
            }
        });

        return view;
    }

    private void loadRandomProfile() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> userIds = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String username = userSnapshot.child("displayName").getValue(String.class);
                    if (username != null && !shownProfile.contains(userSnapshot.getKey())){
                        userIds.add(userSnapshot.getKey());
                    }

                }
                if (!userIds.isEmpty()) {
                    Random random = new Random();
                    String randomUserId = userIds.get(random.nextInt(userIds.size()));
                    DatabaseReference userRef = databaseRef.child(randomUserId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String profilePic = snapshot.child("profilePic").getValue(String.class);
                            String displayName = snapshot.child("displayName").getValue(String.class);
                            if (profilePic != null && displayName != null) {
                                Glide.with(getContext())
                                        .load(profilePic)
                                        .into(profilePicSwipe);
                                displayNameSwipe.setText(displayName);

                                shownProfile.add(randomUserId);

                                if (skip == true){
                                    Log.d("HomeFragment", "added to skip: " + prevProfile + " current ID: " + randomUserId);
                                    skipList.add(prevProfile);
                                }
                                prevProfile = randomUserId;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Log.d("HomeFragement", "onCancelled: " + error.getMessage());
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("HomeFragment", "onCancelled: " + error.getMessage());
            }
        });

    }


}