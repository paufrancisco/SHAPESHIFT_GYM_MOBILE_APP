package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.fragments.Login;
import com.jtdev.shape_shift.fragments.SignUp;
import com.jtdev.shape_shift.adapter.vpAdapter_Login;

public class act_Login extends AppCompatActivity {

    private ViewPager viewPager;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_and_signup);

        Intent intent = getIntent();
        int tab = intent.getIntExtra("tab",0);



        viewPager = findViewById(R.id.view_pager);

        frameLayout = findViewById(R.id.main);


        vpAdapter_Login vpAdapter = new vpAdapter_Login(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragments(new Login(), "Login");
        vpAdapter.addFragments(new SignUp(), "Sign Up");
        viewPager.setAdapter(vpAdapter);


        TabLayout tabLayout = findViewById(R.id.tab);

        tabLayout.setupWithViewPager(viewPager);

        if(tab==1){
            viewPager.setCurrentItem(tab);
            frameLayout.setBackgroundResource(R.drawable.bg_signup);
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateFrameLayoutBackground(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void updateFrameLayoutBackground(int tabPosition) {

        switch (tabPosition) {
            case 0:
                frameLayout.setBackgroundResource(R.color.black);
                break;
            case 1:
                frameLayout.setBackgroundResource(R.drawable.bg_signup);
                break;

            default:
                frameLayout.setBackgroundResource(R.color.black);
                break;
        }
    }
}
