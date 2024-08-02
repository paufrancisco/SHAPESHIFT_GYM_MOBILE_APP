package com.jtdev.shape_shift.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.activity.act_bmiCalculator;
import com.jtdev.shape_shift.activity.act_getStarted;
import com.jtdev.shape_shift.adapter.rcAdapter_Rewards;
import com.jtdev.shape_shift.model.model_Rewards;

import java.util.ArrayList;

public class Profile extends Fragment {

    TextView name, bmi, height1, weight1, program, calories, hours;
    Button button, logoutButton;
    ImageView imageView4;
    RecyclerView rcRewards;

    ArrayList<model_Rewards> modelRewardsArrayList;
    rcAdapter_Rewards rcAdapterRewards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);

        name = view.findViewById(R.id.displayName);
        button = view.findViewById(R.id.editBMI);
        bmi = view.findViewById(R.id.displayBMI);
        height1 = view.findViewById(R.id.displayHeight);
        weight1 = view.findViewById(R.id.displayWeight);
        program = view.findViewById(R.id.displayProgram);
        imageView4 = view.findViewById(R.id.imageView4);
        calories = view.findViewById(R.id.tvCalories);
        hours = view.findViewById(R.id.tvWorkoutHours);
        rcRewards = view.findViewById(R.id.rcRewards);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("BMI CHART");

                View view = LayoutInflater.from(requireContext()).inflate(R.layout.image_preview_layout, null);
                ImageView imageViewPreview = view.findViewById(R.id.imageViewPreview);
                imageViewPreview.setImageResource(R.drawable.picture_bmi);

                builder.setView(view);

                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());

        button.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), act_bmiCalculator.class));
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference usersData = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

            usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()
                            && snapshot.hasChild("profile")
                            && snapshot.hasChild("user_data")) {

                        String name1 = snapshot.child("profile").child("name").getValue(String.class);
                        String program1 = snapshot.child("user_data").child("program").getValue(String.class);

                        long BMI = snapshot.child("user_data").child("bmi").getValue(Long.class);
                        long heightStr = snapshot.child("user_data").child("height").getValue(Long.class);
                        long weightStr = snapshot.child("user_data").child("weight").getValue(Long.class);

                        bmi.setText(String.valueOf(BMI));
                        height1.setText(String.valueOf(heightStr) + "cm");
                        weight1.setText(String.valueOf(weightStr) + "kg");
                        name.setText(name1.toUpperCase());
                        program.setText(program1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            calculateCaloriesBurned(userId);
            calculateWorkoutHours(userId);
        }

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        modelRewardsArrayList = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


            DatabaseReference doneWorkoutsRef = userRef.child("doneWorkout");
            doneWorkoutsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long totalTimeInMinutes = 0;
                    long workoutCount = dataSnapshot.getChildrenCount();

                    for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                        Long workSets = workoutSnapshot.child("workSets").getValue(Long.class);
                        Long workTimer = workoutSnapshot.child("workTimer").getValue(Long.class);
                        Long workReps = 2L;

                        if (workTimer == 0) {
                            totalTimeInMinutes += workSets * workReps;
                        } else {
                            totalTimeInMinutes += workSets * workTimer;
                        }
                    }

                    long totalTimeInHours = totalTimeInMinutes / 60;

                    if (totalTimeInHours >= 10) {
                        modelRewardsArrayList.add(new model_Rewards("10-Hour Fitness Champion", R.drawable.hour_reward_10));
                    }
                    if (totalTimeInHours >= 20) {
                        modelRewardsArrayList.add(new model_Rewards("20-Hour Fitness Champion", R.drawable.hour_reward_20));
                    }
                    if (totalTimeInHours >= 30) {
                        modelRewardsArrayList.add(new model_Rewards("30-Hour Fitness Champion", R.drawable.hour_reward_30));
                    }

                    if (workoutCount >= 4) {
                        modelRewardsArrayList.add(new model_Rewards("5 Workouts Completed", R.drawable.workout_count_20));
                    }
                    if (workoutCount >= 30) {
                        modelRewardsArrayList.add(new model_Rewards("10 Workouts Completed", R.drawable.workout_count_30));
                    }
                    if (workoutCount >= 50) {
                        modelRewardsArrayList.add(new model_Rewards("15 Workouts Completed", R.drawable.workout_count_50));
                    }

                    DatabaseReference challengesRef = userRef.child("challenges");
                    challengesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalDifficulty = 0;

                            for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                                Long difficulty = challengeSnapshot.child("difficulty").getValue(Long.class);
                                if (difficulty != null) {
                                    totalDifficulty += difficulty;
                                }
                            }

                            if (totalDifficulty >= 145) {
                                modelRewardsArrayList.add(new model_Rewards("145 Points Challenge Master", R.drawable.challenge_145));
                            } else if (totalDifficulty >= 100) {
                                modelRewardsArrayList.add(new model_Rewards("100 Points Challenge Master", R.drawable.challenge_100));
                            } else if (totalDifficulty >= 50) {
                                modelRewardsArrayList.add(new model_Rewards("50 Points Challenge Master", R.drawable.challenge_50));
                            }

                            // Set up the RecyclerView with the adapter
                            rcAdapterRewards = new rcAdapter_Rewards(modelRewardsArrayList, getContext());
                            rcRewards.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            rcRewards.setAdapter(rcAdapterRewards);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }





    private void calculateCaloriesBurned(String userId) {
        DatabaseReference doneWorkoutsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("doneWorkout");

        doneWorkoutsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long workoutCount = dataSnapshot.getChildrenCount();
                long totalCalories = workoutCount * 189;
                calories.setText(String.valueOf(totalCalories) + " CAL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void calculateWorkoutHours(String userId) {
        DatabaseReference doneWorkoutsRef = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(userId)
                .child("doneWorkout");

        doneWorkoutsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long totalTimeInSeconds = 0;

                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    Long workSets = workoutSnapshot.child("workSets").getValue(Long.class);
                    Long workTimer = workoutSnapshot.child("workTimer").getValue(Long.class);
                    Long workReps = (long) 450.00;

                    if (workTimer == 0) {
                        totalTimeInSeconds += workSets * workReps;
                    } else {
                        totalTimeInSeconds += workSets * workTimer * workReps;
                    }
                }

                long hours = totalTimeInSeconds / 3600;
                long minutes = (totalTimeInSeconds % 3600) / 60;

                Profile.this.hours.setText(String.valueOf(hours) + " H " + String.valueOf(minutes) + " M");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogbox_logout_confirmation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnOk = dialogView.findViewById(R.id.btn_ok);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), act_getStarted.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void retrieveChallengeNames() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference challengesRef = database.getReference("challenges");

        challengesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) challengeSnapshot.child("name").getValue();
                    System.out.println(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failed to read value." + databaseError.toException());
            }
        });
    }
}
