package com.example.project1.ui.exercises;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.databinding.FragmentExerciseBinding;
import com.example.project1.databinding.FragmentGoalsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link exerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exerciseFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentExerciseBinding binding;
    private View root;
    private EditText exerciseInput;
    private LinearLayout exerciseListMonday;
    private LinearLayout exerciseListTuesday;
    private LinearLayout exerciseListWednesday;
    private LinearLayout exerciseListThursday;
    private LinearLayout exerciseListFriday;
    private LinearLayout exerciseListSaturday;
    private LinearLayout exerciseListSunday;
    private Button addExercise;
    private Button clearExercises;
    private int exerciseNumMonday;
    private int exerciseNumTuesday;
    private int exerciseNumWednesday;
    private int exerciseNumThursday;
    private int exerciseNumFriday;
    private int exerciseNumSaturday;
    private int exerciseNumSunday;
    private String chosenDay;
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public exerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment exerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static exerciseFragment newInstance(String param1, String param2) {
        exerciseFragment fragment = new exerciseFragment();
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
        binding = FragmentExerciseBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        exerciseInput = root.findViewById(R.id.exercise_input);
        exerciseListMonday = root.findViewById(R.id.monday_exercises);
        exerciseListTuesday = root.findViewById(R.id.tuesday_exercises);
        exerciseListWednesday = root.findViewById(R.id.wednesday_exercises);
        exerciseListThursday = root.findViewById(R.id.thursday_exercises);
        exerciseListFriday = root.findViewById(R.id.friday_exercises);
        exerciseListSaturday = root.findViewById(R.id.saturday_exercises);
        exerciseListSunday = root.findViewById(R.id.sunday_exercises);
        addExercise = root.findViewById(R.id.add_exercise_button);
        clearExercises = root.findViewById(R.id.clear_exercises_button);
        exerciseNumMonday = exerciseNumTuesday = exerciseNumWednesday =
                exerciseNumThursday = exerciseNumFriday = exerciseNumSaturday = exerciseNumSunday = 1;
        Spinner daySpinner = root.findViewById(R.id.day_spinner);
        daySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> day = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.
                simple_spinner_dropdown_item, days);
        day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(day);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) view).setTextColor(Color.WHITE);
                chosenDay = parent.getItemAtPosition(position).toString();
                switch (chosenDay) {
                    case "Monday":
                        exerciseListMonday.setVisibility(View.VISIBLE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Tuesday":
                        exerciseListTuesday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Wednesday":
                        exerciseListWednesday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Thursday":
                        exerciseListThursday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Friday":
                        exerciseListFriday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Saturday":
                        exerciseListSaturday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSunday.setVisibility(View.GONE);
                        break;
                    case "Sunday":
                        exerciseListSunday.setVisibility(View.VISIBLE);
                        exerciseListMonday.setVisibility(View.GONE);
                        exerciseListTuesday.setVisibility(View.GONE);
                        exerciseListWednesday.setVisibility(View.GONE);
                        exerciseListThursday.setVisibility(View.GONE);
                        exerciseListFriday.setVisibility(View.GONE);
                        exerciseListSaturday.setVisibility(View.GONE);
                        break;
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                chosenDay = "Monday";
            }
        });
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exerciseText = exerciseInput.getText().toString();
                String chosenDay = daySpinner.getSelectedItem().toString();
                switch (chosenDay) {
                    case "Monday":
                        exerciseListMonday.addView(createExerciseTextView(exerciseText, exerciseNumMonday));
                        exerciseNumMonday++;
                        break;
                    case "Tuesday":
                        exerciseListTuesday.addView(createExerciseTextView(exerciseText, exerciseNumTuesday));
                        exerciseNumTuesday++;
                        break;
                    case "Wednesday":
                        exerciseListWednesday.addView(createExerciseTextView(exerciseText, exerciseNumWednesday));
                        exerciseNumWednesday++;
                        break;
                    case "Thursday":
                        exerciseListThursday.addView(createExerciseTextView(exerciseText, exerciseNumThursday));
                        exerciseNumThursday++;
                        break;
                    case "Friday":
                        exerciseListFriday.addView(createExerciseTextView(exerciseText, exerciseNumFriday));
                        exerciseNumFriday++;
                        break;
                    case "Saturday":
                        exerciseListSaturday.addView(createExerciseTextView(exerciseText, exerciseNumSaturday));
                        exerciseNumSaturday++;
                        break;
                    case "Sunday":
                        exerciseListSunday.addView(createExerciseTextView(exerciseText, exerciseNumSunday));
                        exerciseNumSunday++;
                        break;
                }

                exerciseInput.setText("");

            }
        });
        clearExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDay = daySpinner.getSelectedItem().toString();
                switch (chosenDay) {
                    case "Monday":
                        exerciseListMonday.removeAllViews();
                        exerciseNumMonday = 1;
                        break;
                    case "Tuesday":
                        exerciseListTuesday.removeAllViews();
                        exerciseNumTuesday = 1;
                        break;
                    case "Wednesday":
                        exerciseListWednesday.removeAllViews();
                        exerciseNumWednesday = 1;
                        break;
                    case "Thursday":
                        exerciseListThursday.removeAllViews();
                        exerciseNumThursday = 1;
                        break;
                    case "Friday":
                        exerciseListFriday.removeAllViews();
                        exerciseNumFriday = 1;
                        break;
                    case "Saturday":
                        exerciseListSaturday.removeAllViews();
                        exerciseNumSaturday = 1;
                        break;
                    case "Sunday":
                        exerciseListSunday.removeAllViews();
                        exerciseNumSunday = 1;
                        break;
                }
            }
        });

        return root;
    }

    private View createExerciseTextView(String str, Integer exerciseNum) {
        TextView newExercise = new TextView(getActivity().getApplicationContext());
        newExercise.setText(exerciseNum + ". " + str);
        newExercise.setTextColor(Color.WHITE);
        newExercise.setTextSize(24);
        newExercise.setPadding(0, 16, 0, 16);
        return newExercise;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}