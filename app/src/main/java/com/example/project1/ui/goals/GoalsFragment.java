package com.example.project1.ui.goals;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project1.UserProfile;
import com.google.firebase.database.DataSnapshot;


import com.example.project1.R;
import com.example.project1.databinding.FragmentGoalsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsFragment extends Fragment {

    private FragmentGoalsBinding binding;
    private View root;
    private EditText goalInput;
    private LinearLayout goalList;
    private Button addGoal;
    private Button clearGoals;
    private int goalNum;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalsFragment newInstance(String param1, String param2) {
        GoalsFragment fragment = new GoalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                List<String> savedGoalList = userProfile.getGoalList();

                // Clear any existing views in the goalList LinearLayout
                goalList.removeAllViews();

                if (savedGoalList != null) {
                    goalNum = 1;
                    // Iterate through each goal and create a new TextView for each one
                    for (int i = 0; i < savedGoalList.size(); i++) {
                        String goalText = savedGoalList.get(i);
                        TextView newGoal = new TextView(getActivity().getApplicationContext());
                        newGoal.setText(goalText);
                        newGoal.setTextColor(Color.WHITE);
                        newGoal.setTextSize(20);
                        newGoal.setPadding(0, 16, 0, 16);

                        // Add the new goal TextView to the goalList LinearLayout
                        goalList.addView(newGoal);
                        goalNum++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        addGoal = root.findViewById(R.id.add_goal);
        goalInput = root.findViewById(R.id.goal_input);
        clearGoals = root.findViewById(R.id.clear_goal);
        goalList = root.findViewById(R.id.goal_list);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalText = goalInput.getText().toString();
                TextView newGoal = new TextView(getActivity().getApplicationContext());
                newGoal.setText(goalNum + ". " + goalText);
                newGoal.setTextColor(Color.WHITE);
                newGoal.setTextSize(24);
                newGoal.setPadding(0,16,0,16);

                // Add the new goal TextView to the goalList LinearLayout
                goalList.addView(newGoal);
                // Clear the goalInput EditText
                goalInput.setText("");
                saveGoalList();
            }
        });
        clearGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalList.removeAllViews();
                goalNum = 1;
                saveGoalList();
            }
        });
        return root;
    }
    private void saveGoalList() {
        List<String> goals = new ArrayList<>();
        for (int i = 0; i < goalList.getChildCount(); i++) {
            View childView = goalList.getChildAt(i);
            if (childView instanceof TextView) {
                String goalText = ((TextView) childView).getText().toString();
                goals.add(goalText);
            }
        }
        myRef.child("goalList").setValue(goals);
    }
}