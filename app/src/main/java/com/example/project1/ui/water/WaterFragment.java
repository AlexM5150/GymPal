package com.example.project1.ui.water;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project1.R;

import java.util.Locale;

public class WaterFragment extends Fragment {
    private static final String PREFS_NAME = "water_tracker_prefs";
    private static final String KEY_WATER_INTAKE = "water_intake";

    private int waterIntake = 0;
    private TextView waterIntakeTextView;
    private ImageView waterCupImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);

        waterIntakeTextView = view.findViewById(R.id.waterIntakeTextView);
        waterCupImageView = view.findViewById(R.id.waterCupImageView);

        // Load saved water intake from SharedPreferences
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        waterIntake = prefs.getInt(KEY_WATER_INTAKE, 0);
        updateWaterIntake();

        Button addButton = view.findViewById(R.id.incrementButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterIntake++;
                updateWaterIntake();
                animateWaterCup();
                saveWaterIntake();
            }
        });

        Button subtractButton = view.findViewById(R.id.decrementButton);
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (waterIntake > 0) {
                    waterIntake--;
                    updateWaterIntake();
                    animateWaterCup();
                    saveWaterIntake();
                }
            }
        });

        return view;
    }

    private void updateWaterIntake() {
        waterIntakeTextView.setText(getString(R.string.water_intake_format, waterIntake));
    }

    private void animateWaterCup() {
        if (waterIntake == 0) {
            waterCupImageView.setImageResource(R.drawable.water_cup_empty);
        } else if (waterIntake <= 2) {
            waterCupImageView.setImageResource(R.drawable.water_cup_25);
        } else if (waterIntake <= 4) {
            waterCupImageView.setImageResource(R.drawable.water_cup_50);
        } else if (waterIntake <= 7) {
            waterCupImageView.setImageResource(R.drawable.water_cup_75);
        } else {
            waterCupImageView.setImageResource(R.drawable.water_cup_full);
        }

    }

    private void saveWaterIntake() {
        // Save current water intake to SharedPreferences
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_WATER_INTAKE, waterIntake);
        editor.apply();
    }
}