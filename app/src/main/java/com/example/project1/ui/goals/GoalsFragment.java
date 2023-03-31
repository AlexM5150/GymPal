package com.example.project1.ui.goals;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.example.project1.R;
import com.example.project1.databinding.FragmentGoalsBinding;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        addGoal = root.findViewById(R.id.add_goal);
        goalInput = root.findViewById(R.id.goal_input);
        clearGoals = root.findViewById(R.id.clear_goal);
        goalList = root.findViewById(R.id.goal_list);
        goalNum = 1;
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
                goalNum++;
                // Clear the goalInput EditText
                goalInput.setText("");
            }
        });
        clearGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalList.removeAllViews();
            }
        });
        return root;
    }
}