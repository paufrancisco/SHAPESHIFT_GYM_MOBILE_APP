package com.jtdev.shape_shift.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jtdev.shape_shift.model.model_Challenge;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.adapter.rcAdapter_Challenge;
import java.util.ArrayList;

public class Challenge extends Fragment {

    RecyclerView recyclerView0, recyclerView1, recyclerView2;
    ArrayList<model_Challenge> modelArrayList1;
    ArrayList<model_Challenge> modelArrayList2;
    ArrayList<model_Challenge> modelArrayList3;
    rcAdapter_Challenge rcAdapter;

    TextView name, displayPoints;
    int totalDifficulty;
    ImageView icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_challenge, container, false);

        name = view.findViewById(R.id.uname);
        displayPoints = view.findViewById(R.id.displayPoints);
        icon = view.findViewById(R.id.ivIcon);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            DatabaseReference challengesRef = userRef.child("challenges");
            DatabaseReference profileRef = userRef.child("profile");
            DatabaseReference userDataRef = userRef.child("user_data");


            challengesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    totalDifficulty = 0;
                    for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                        Integer difficulty = challengeSnapshot.child("difficulty").getValue(Integer.class);
                        if (difficulty != null) {
                            totalDifficulty += difficulty;
                        }
                    }
                    displayPoints.setText(String.valueOf(totalDifficulty));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userName = snapshot.child("name").getValue(String.class);

                    if (userName != null && !userName.isEmpty()) {
                        String newUserName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
                        name.setText(newUserName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String gender = snapshot.child("gender").getValue(String.class);

                    if ("Male".equalsIgnoreCase(gender)) {
                        icon.setImageResource(R.drawable.male_profile); // Make sure these resources exist
                    } else if ("Female".equalsIgnoreCase(gender)) {
                        icon.setImageResource(R.drawable.female_profile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        // Initialization
        recyclerView0 = view.findViewById(R.id.rc_easy);
        recyclerView1 = view.findViewById(R.id.rc_medium);
        recyclerView2 = view.findViewById(R.id.rc_Hard);

        // RecyclerViews
        fetchDataEasy();
        fetchDataMedium();
        fetchDataHard();

        return view;
    }

    public void fetchDataEasy() {
        modelArrayList1 = new ArrayList<>();
        modelArrayList1.add(new model_Challenge(5, "3mins Plank", R.drawable.ic_plank));
        modelArrayList1.add(new model_Challenge(5, "10km Sprint", R.drawable.ic_sprint));
        modelArrayList1.add(new model_Challenge(5, "2mins Squat", R.drawable.ic_squat));
        modelArrayList1.add(new model_Challenge(5, "Hydration", R.drawable.ic_water));
        modelArrayList1.add(new model_Challenge(5, "Back Strength", R.drawable.ic_backstretch));

        rcAdapter = new rcAdapter_Challenge(modelArrayList1, getContext());
        recyclerView0.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView0.setAdapter(rcAdapter);
    }

    public void fetchDataMedium() {
        modelArrayList2 = new ArrayList<>();
        modelArrayList2.add(new model_Challenge(10, "20 Push ups", R.drawable.ic_pushup));
        modelArrayList2.add(new model_Challenge(10, "20 Curl ups", R.drawable.ic_curlup));
        modelArrayList2.add(new model_Challenge(10, "20 Leg Raise", R.drawable.ic_legraise));
        modelArrayList2.add(new model_Challenge(10, "20km Sprint", R.drawable.ic_sprint));
        modelArrayList2.add(new model_Challenge(10, "3mins Squat", R.drawable.ic_squat));

        rcAdapter = new rcAdapter_Challenge(modelArrayList2, getContext());
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(rcAdapter);
    }

    public void fetchDataHard() {
        modelArrayList3 = new ArrayList<>();
        modelArrayList3.add(new model_Challenge(15, "100 Push ups", R.drawable.ic_pushup));
        modelArrayList3.add(new model_Challenge(15, "Win Arm wrestling", R.drawable.ic_armwrestling));
        modelArrayList3.add(new model_Challenge(15, "5mins Plank", R.drawable.ic_plank));
        modelArrayList3.add(new model_Challenge(15, "30km Sprint", R.drawable.ic_sprint));
        modelArrayList3.add(new model_Challenge(15, "Deadlift", R.drawable.ic_deadlift));

        rcAdapter = new rcAdapter_Challenge(modelArrayList3, getContext());
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(rcAdapter);
    }
}
