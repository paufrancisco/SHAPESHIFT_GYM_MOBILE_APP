package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.fragments.Challenge;
import com.jtdev.shape_shift.fragments.Home;
import com.jtdev.shape_shift.fragments.Profile;
import com.jtdev.shape_shift.fragments.WorkoutPlan;

public class act_dashBoard extends AppCompatActivity {

    FrameLayout frameLayout;

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dashboard);




        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bot_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.home){
                    loadFrag(new Home(),false);
                }else if (id == R.id.workout){
                    loadFrag(new WorkoutPlan(),false);
                }
                else if (id == R.id.challenge){
                    loadFrag(new Challenge(),false);
                }
                else{
                    loadFrag(new Profile(),false);
                }


                return true;
            }
        });

        Intent intent = getIntent();
        String pass = intent.getStringExtra("goods");

        if(pass==null){
            loadFrag(new Home(),true);
        } else if (pass.equalsIgnoreCase("goods")) {
            loadFrag(new Challenge(),true);
        }


    }

    void loadFrag (Fragment fragment, boolean isClicked){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isClicked){
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();

    }


}