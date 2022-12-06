package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// This class represents the user info input page, it will take all the values of the user and input
// the data into the real time firebase data
public class UserInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] gen = {"Male", "Female"};
    String[] act_lvl = {"Low", "Moderate", "High"};
    private Button finished;
    private Context context;
    private EditText feet;
    private EditText inches;
    private Slider currentweight;
    private Slider goalweight;
    private EditText age;

    String profilePic = "nice";
    String feetNum;
    String inchNum;
    String curWeight;
    String gWeight;
    String aLevel;
    String gender;
    String userAge;
    String caloriesLeft;
    String calories;
    String prevCalories;
    String foodName;
    String foodBrand;
    int caloriesLeftNum;
    double height;
    double kgWeight;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        feet = findViewById(R.id.feet);
        inches = findViewById(R.id.inches);
        currentweight = findViewById(R.id.current_weight_slider);
        goalweight = findViewById(R.id.goal_weight_slider);
        age = findViewById(R.id.age_input);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();




        // Spinner creation for activity level and gender
        Spinner actspin = findViewById(R.id.act_level);
        actspin.setOnItemSelectedListener(this);
        ArrayAdapter<String> levels = new ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, act_lvl);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actspin.setAdapter(levels);

        Spinner genderspin = findViewById(R.id.gender_spin);
        actspin.setOnItemSelectedListener(this);
        ArrayAdapter<String> genders= new ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, gen);
        levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspin.setAdapter(genders);

        // Spinner text settings for activity level and gender
        actspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) view).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        genderspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) view).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });


        // When concluded, finish button will send all data into realtime database
        context = getApplicationContext();
        finished = findViewById(R.id.finish_user);
        finished.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                feetNum = feet.getText().toString();
                inchNum = inches.getText().toString();
                curWeight = String.valueOf(currentweight.getValue());
                gWeight = String.valueOf(goalweight.getValue());
                aLevel = actspin.getSelectedItem().toString();
                gender = genderspin.getSelectedItem().toString();
                userAge = age.getText().toString();
                profilePic = "android.resource://com.example.project1/2131165272";
                foodName = "0";
                foodBrand = "0";
                calories = "0";
                prevCalories = "0";

                // Setting the necessary values for the total calories the user needs left for the day
                height = (Integer.parseInt(feetNum)*30.48) + (Integer.parseInt(inchNum)*2.54);
                kgWeight = (Double.parseDouble(curWeight)*0.453592);
                if(gender == "Male") {
                    caloriesLeftNum = (int)(10*(Double.parseDouble(gWeight)) + (6.25*height) - (5*(Integer.parseInt(userAge))) + 5);
                }
                else {
                    caloriesLeftNum = (int)((10*(Double.parseDouble(gWeight))) + (6.25*height) - (5*(Integer.parseInt(userAge))) - 161);
                }

                if(aLevel == "Low") {
                    caloriesLeftNum += 100;
                }
                if(aLevel == "Moderate") {
                    caloriesLeftNum += 150;
                }
                if(aLevel == "High") {
                    caloriesLeftNum += 200;
                }
                //lose 1 lb per week
                if(Double.parseDouble(gWeight) < Double.parseDouble(curWeight)) {
                    caloriesLeft = String.valueOf(caloriesLeftNum -= 500);
                }
                //gain 1lb per week
                else
                    caloriesLeft = String.valueOf(caloriesLeftNum += 500);



                // Checks to see if the user correctly entered all the data, if so it will send to real time database
                if(TextUtils.isEmpty(feetNum) || TextUtils.isEmpty(inchNum) ||
                        TextUtils.isEmpty(curWeight) || TextUtils.isEmpty(gWeight)){
                    Toast.makeText(context, "Please fill all fields."
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    sendUserData();
                    Intent done = new Intent(context, Navigation.class);
                    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                    String displayName = user.getDisplayName();
                    done.putExtra("displayName", displayName);
                    finish();
                    startActivity(done);
                    Toast.makeText(context, "Your account has been created!"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // This method will send the data to the realtime firebase data based on User ID.
    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(feetNum, inchNum, curWeight, gWeight ,aLevel,
                gender, caloriesLeft, userAge, profilePic, foodName, calories, foodBrand, prevCalories);
        myRef.setValue(userProfile);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}