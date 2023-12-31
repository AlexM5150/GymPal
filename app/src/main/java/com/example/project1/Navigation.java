package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.project1.ui.calories.CaloriesFragment;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.databinding.ActivityNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import static androidx.navigation.Navigation.findNavController;

public class Navigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    private DrawerLayout drawer;
    private TextView drawerUsername;
    public static NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userData;
    private String user;
    public static ImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigation.toolbar);

        //create hamburger menu
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.drawer_home, R.id.drawer_search, R.id.drawer_help, R.id.drawer_food, R.id.drawer_logout, R.id.drawer_stats, R.id.drawer_water, R.id.drawer_contact, R.id.drawer_exercises)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // call if logout is clicked
        logoutClick();

        View headerView = navigationView.getHeaderView(0);
        drawerUsername = headerView.findViewById(R.id.drawer_username);
        user = getIntent().getStringExtra("displayName");
        drawerUsername.setText("Welcome " + user);
        profilepic = headerView.findViewById(R.id.navProfilePic);



    }

    public void onClick(View v) {
        Intent profile = new Intent(getApplicationContext(), Profile.class);
        startActivity(profile);
    }

    public void logoutClick() {
        navigationView.getMenu().findItem(R.id.drawer_logout).setOnMenuItemClickListener(menuItem -> {
            firebaseAuth.signOut();
            Intent logout = new Intent(getApplicationContext(), Login.class);
            finish();
            startActivity(logout);
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        switch(item.getItemId()) {
            case R.id.action_about:
                String selection = item.getTitle().toString();
                if(selection.equals("Developer")) {
                    Intent devInfo = new Intent(context, DevInfo.class);
                    startActivity(devInfo);
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}