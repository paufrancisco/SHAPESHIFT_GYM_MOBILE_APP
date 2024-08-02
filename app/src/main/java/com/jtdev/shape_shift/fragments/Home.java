package com.jtdev.shape_shift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.jtdev.shape_shift.adapter.rcAdapter_TodaysWorkout;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.model.model_Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Home extends Fragment {

    rcAdapter_TodaysWorkout rcAdapterHome;
    RecyclerView recyclerView;
    TextView displayDate,dayStatus,nextExercise,displayCurrentProgram;
    ArrayList<model_Workout> modelArrayList;
    ImageView ivProgram;
    boolean isRestDay = false;

    CardView cvHome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = view.findViewById(R.id.rcHome);
        displayDate = view.findViewById(R.id.displayDate);
        dayStatus = view.findViewById(R.id.dayStatus);
        nextExercise = view.findViewById(R.id.displayTitle);
        displayCurrentProgram = view.findViewById(R.id.displayCurrentProgram);
        ivProgram = view.findViewById(R.id.ivProgram);
        cvHome = view.findViewById(R.id.cvHome);


        String currentDay = getCurrentDayName();
        displayDate.setText(currentDay);

        fetchHealthStatusFromFirebase(currentDay);

        return view;
    }

    private String getCurrentDayName() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = dateFormat.format(calendar.getTime());
        return dayName.substring(0, 3).toUpperCase(Locale.getDefault());
    }

    private void fetchHealthStatusFromFirebase(String currentDay) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference usersData = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("user_data").child("program");
            usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String program1 = snapshot.getValue(String.class);
                        if ("Gain Weight".equalsIgnoreCase(program1)) {
                            fetchWorkoutUnderWeight(currentDay);
                        } else if("Maintain Healthy".equalsIgnoreCase(program1))
                        {
                            fetchWorkoutNormal(currentDay);
                        }else if("Lose Weight".equalsIgnoreCase(program1))
                        {
                            fetchWorkoutOverWeight(currentDay);
                        }else if("Significant Weight Lose".equalsIgnoreCase(program1))
                        {
                            fetchWorkoutObese(currentDay);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        }
    }

    public void fetchWorkoutUnderWeight(String day) {
        modelArrayList = new ArrayList<>();
        switch (day) {
            case "MON":
                modelArrayList.add(new model_Workout("1", "Surrenders", "12-15 reps per set",
                        R.drawable.burpees_video, R.drawable.burpees_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("2", "Push Up", "12-15 reps per set",
                        R.drawable.push_up_video, R.drawable.push_up_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("3", "Inclined Push Up", "12-15 reps per set",
                        R.drawable.bench_push_ups_video, R.drawable.bench_push_ups_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("4", "Body Weight Squat", "12-15 reps per set",
                        R.drawable.bodyweight_squat_video, R.drawable.bodyweight_squat_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("5", "Situps", "30 seconds per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image, 3, "30 seconds", 30000));
                break;

            case "TUE":
                modelArrayList.add(new model_Workout("1", "Situps", "30 seconds per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("2", "Crunches", "30 seconds per set",
                        R.drawable.crunches_video, R.drawable.crunches_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("3", "Flutter Kicks", "30 seconds per set",
                        R.drawable.flutter_kicks_video, R.drawable.flutter_kicks_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("4", "Leg Raise", "30 seconds per set",
                        R.drawable.leg_raise_video, R.drawable.leg_raise_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("5", "Burpees", "12 reps per set",
                        R.drawable.burpees_video, R.drawable.burpees_image, 3, "12 reps", 0));
                break;

            case "WED":
                modelArrayList.add(new model_Workout("1", "Push Ups", "12-15 reps per set",
                        R.drawable.push_up_video, R.drawable.push_up_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("2", "Body Weight Squats", "12-15 reps per set",
                        R.drawable.bodyweight_squat_video, R.drawable.bodyweight_squat_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("3", "Body Weight Row", "12-15 reps per set",
                        R.drawable.bodyweight_row_video, R.drawable.bodyweight_row_image, 3, "12-15 reps", 0));
                break;

            case "THU":
                modelArrayList.add(new model_Workout("1", "Situps", "30 seconds per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("2", "Russian Twist", "30 seconds per set",
                        R.drawable.russian_twist_video, R.drawable.russian_twist_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("3", "Planking", "45 seconds per set",
                        R.drawable.planking_video, R.drawable.planking_image, 3, "45 seconds", 45000));
                modelArrayList.add(new model_Workout("4", "Mountain Climbers", "30 seconds per set",
                        R.drawable.mountain_climber_video, R.drawable.mountain_climber_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("5", "Leg Raise", "30 seconds per set",
                        R.drawable.leg_raise_video, R.drawable.leg_raise_image, 3, "30 seconds", 30000));
                break;

            case "FRI":
                modelArrayList.add(new model_Workout("1", "Push Ups", "12-15 reps per set",
                        R.drawable.push_up_video, R.drawable.push_up_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("2", "Inclined Push Up", "12-15 reps per set",
                        R.drawable.incline_bench_press_video, R.drawable.incline_bench_press_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("3", "Body Squat", "12-15 reps per set",
                        R.drawable.bodyweight_row_video, R.drawable.bodyweight_row_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("4", "Burpees", "12 reps per set",
                        R.drawable.burpees_video, R.drawable.burpees_image, 3, "12 reps", 0));
                break;

            case "SAT", "SUN":
                isRestDay = true;
                dayStatus.setText("Rest Day");
                displayCurrentProgram.setText("No Training Yet");
                ivProgram.setImageResource(R.drawable.bg_restday);
                nextExercise.setText("Reminders");
                modelArrayList.add(new model_Workout(
                        "1", "Boost muscle repair", "Take a cold shower","rest"
                ));
                modelArrayList.add(new model_Workout(
                        "2", "Stretching", "Stretch your legs and arms for 10 minutes","rest"
                ));
                modelArrayList.add(new model_Workout(
                        "3", "Rest and Recover", "Take time to relax and let your body recover","rest"
                ));
                break;
        }

        rcAdapterHome = new rcAdapter_TodaysWorkout(getContext(), modelArrayList, isRestDay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rcAdapterHome);
    }



    public void fetchWorkoutNormal(String day) {
        modelArrayList = new ArrayList<>();
        boolean isRestDay = false;

        switch (day) {
            case "MON":
                modelArrayList.add(new model_Workout("1", "Diamond Pushup", "12-15 reps per set",
                        R.drawable.diamond_push_ups_video, R.drawable.diamond_push_ups_image, 3, "12-15 reps", 0));

                modelArrayList.add(new model_Workout("2", "Squats", "12-15 reps per set",
                        R.drawable.squat_video, R.drawable.squat_image, 3, "12-15 reps", 0));

                modelArrayList.add(new model_Workout("3", "Pushups", "12-15 reps per set",
                        R.drawable.push_up_video, R.drawable.pull_up_image, 3, "12-15 reps", 0));

                modelArrayList.add(new model_Workout("4", "Pull up", "12-15 reps per set",
                        R.drawable.pull_up_video, R.drawable.pull_up_image, 3, "12-15 reps", 0));

                modelArrayList.add(new model_Workout("5", "Burpees", "12-15 reps per set",
                        R.drawable.burpees_video, R.drawable.burpees_image, 3, "12-15 reps", 0));
                break;

            case "TUE":
                modelArrayList.add(new model_Workout("1", "Situps", "30 seconds per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("2", "Crunches", "30 seconds per set",
                        R.drawable.crunches_video, R.drawable.crunches_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("3", "Leg Raise", "30 seconds per set",
                        R.drawable.leg_raise_video, R.drawable.leg_raise_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("4", "Planking", "45 seconds per set",
                        R.drawable.planking_video, R.drawable.planking_image, 3, "45 seconds", 45000));
                break;

            case "WED":
                modelArrayList.add(new model_Workout("1", "Squats", "12-15 reps per set",
                        R.drawable.squat_video, R.drawable.squat_image, 3, "12-15 reps", 0));

                modelArrayList.add(new model_Workout("2", "Jogging", "1 hour",
                        R.drawable.jogging_video, R.drawable.jogging_image, 1, "1 hour", 3600000));

                modelArrayList.add(new model_Workout("3", "Air Squat", "12-15 reps per set",
                        R.drawable.air_squat_video, R.drawable.air_squat_image, 3, "12-15 reps", 0));
                break;

            case "THU":
                modelArrayList.add(new model_Workout("1", "Crunches", "30 seconds per set",
                        R.drawable.crunches_video, R.drawable.crunches_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("2", "Mountain Climbers", "30 seconds per set",
                        R.drawable.mountain_climber_video, R.drawable.mountain_climber_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("3", "Russian Twist", "30 seconds per set",
                        R.drawable.russian_twist_video, R.drawable.russian_twist_image, 3, "30 seconds", 30000));

                modelArrayList.add(new model_Workout("4", "Leg Raise", "30 seconds per set",
                        R.drawable.leg_raise_video, R.drawable.leg_raise_image, 3, "30 seconds", 30000));
                break;

            case "FRI":
            case "SAT":
            case "SUN":
                isRestDay = true;
                dayStatus.setText("Rest Day");
                displayCurrentProgram.setText("No Training Yet");
                ivProgram.setImageResource(R.drawable.bg_restday);
                nextExercise.setText("Reminders");
                modelArrayList.add(new model_Workout(
                        "1", "Boost muscle repair", "Take a cold shower", "rest"
                ));
                modelArrayList.add(new model_Workout(
                        "2", "Stretching", "Stretch your legs and arms for 10 minutes", "rest"
                ));
                modelArrayList.add(new model_Workout(
                        "3", "Rest and Recover", "Take time to relax and let your body recover", "rest"
                ));
                break;
        }

        rcAdapterHome = new rcAdapter_TodaysWorkout(getContext(), modelArrayList, isRestDay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rcAdapterHome);
    }



    public void fetchWorkoutOverWeight(String day) {
        modelArrayList = new ArrayList<>();
        boolean isRestDay = false;

        switch (day) {
            case "MON":
                modelArrayList.add(new model_Workout("1", "Pushup", "12-15 reps per set",
                        R.drawable.push_up_video, R.drawable.push_up_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("2", "Diamond Pushup", "12-15 reps per set",
                        R.drawable.diamond_push_ups_video, R.drawable.diamond_push_ups_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("3", "Squat", "12-15 reps per set",
                        R.drawable.squat_video, R.drawable.squat_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("4", "Burpees", "12-15 reps per set",
                        R.drawable.burpees_video, R.drawable.burpees_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("5", "Inclined Push up", "12-15 reps per set",
                        R.drawable.incline_bench_press_video, R.drawable.incline_bench_press_image, 3, "12-15 reps", 0));
                break;

            case "TUE", "THU", "SUN":
                isRestDay = true;
                dayStatus.setText("Rest Day");
                displayCurrentProgram.setText("No Training Yet");
                ivProgram.setImageResource(R.drawable.bg_restday);
                nextExercise.setText("Reminders");
                modelArrayList.add(new model_Workout(
                        "1", "Boost muscle repair", "Take a cold shower","rest"
                ));
                modelArrayList.add(new model_Workout(
                        "2", "Stretching", "Stretch your legs and arms for 10 minutes","rest"
                ));

                modelArrayList.add(new model_Workout(
                        "3", "Rest and Recover", "Take time to relax and let your body recover","rest"
                ));
                break;

            case "WED":
                modelArrayList.add(new model_Workout("1", "Squat", "12-15 reps per set",
                        R.drawable.squat_video, R.drawable.squat_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("2", "Air Squat", "12-15 reps per set",
                        R.drawable.air_squat_video, R.drawable.air_squat_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("3", "Front leg raise", "12-15 reps per set",
                        R.drawable.flutter_kicks_video, R.drawable.flutter_kicks_image, 3, "12-15 reps", 0));
                modelArrayList.add(new model_Workout("4", "Jumping Jacks", "12-15 reps",
                        R.drawable.jumping_jack_video, R.drawable.jumping_jack_image, 3, "12-15 reps", 30000));
                modelArrayList.add(new model_Workout("5", "Walking", "1 hour",
                        R.drawable.walking_video, R.drawable.walking_image, 1, "1 hour", 3600000));
                break;

            case "FRI":
                modelArrayList.add(new model_Workout("1", "Cardio", "30 minutes - 1 hour",
                        R.drawable.cardio_video, R.drawable.cardio_image, 1, "30 minutes - 1 hour", 1800000));
                modelArrayList.add(new model_Workout("2", "Jumping Jacks", "12-15 reps",
                        R.drawable.jumping_jack_video, R.drawable.jumping_jack_image, 3, "12-15 reps", 30000));
                modelArrayList.add(new model_Workout("3", "Walking", "1 hour",
                        R.drawable.walking_video, R.drawable.walking_image, 1, "1 hour", 3600000));
                break;

            case "SAT":
                modelArrayList.add(new model_Workout("1", "Situps", "30 seconds per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("2", "Crunches", "30 seconds per set",
                        R.drawable.crunches_video, R.drawable.crunches_image, 3, "30 seconds", 30000));
                modelArrayList.add(new model_Workout("3", "Planking", "45 seconds per set",
                        R.drawable.planking_video, R.drawable.planking_image, 3, "45 seconds", 45000));
                modelArrayList.add(new model_Workout("4", "Russian twist", "30 seconds per set",
                        R.drawable.russian_twist_video, R.drawable.russian_twist_image, 3, "30 seconds", 30000));
                break;

        }

        rcAdapterHome = new rcAdapter_TodaysWorkout(getContext(), modelArrayList, isRestDay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rcAdapterHome);
    }



    public void fetchWorkoutObese(String day) {
        modelArrayList = new ArrayList<>();
        //public model_Workout(String workoutNumber, String workoutTitle, String workoutDescription, int workoutGIPH, int workPlaceholder,int workSets, String workRepetition, int workTimer)
        switch (day) {
            case "MON":
                modelArrayList.add(new model_Workout("1", "Walking", "1 hour",
                        R.drawable.walking_video, R.drawable.walking_image,1,"",3600000));
                modelArrayList.add(new model_Workout("2", "Brisk Walking", "1 hour",
                        R.drawable.brisk_walking_video, R.drawable.brisk_walking_image,1,"",3600000));
                modelArrayList.add(new model_Workout("3", "Cardio ", "1 hour",
                        R.drawable.cardio_video, R.drawable.cardio_image,1,"12",3600000));
                break;

            case "TUE", "THU", "SUN":
                isRestDay = true;
                dayStatus.setText("Rest Day");
                displayCurrentProgram.setText("No Training Yet");
                ivProgram.setImageResource(R.drawable.bg_restday);
                nextExercise.setText("Reminders");
                modelArrayList.add(new model_Workout(
                        "1", "Boost muscle repair", "Take a cold shower","rest"
                ));
                modelArrayList.add(new model_Workout(
                        "2", "Stretching", "Stretch your legs and arms for 10 minutes","rest"
                ));

                modelArrayList.add(new model_Workout(
                        "3", "Rest and Recover", "Take time to relax and let your body recover","rest"
                ));
                break;

            case "WED":
                modelArrayList.add(new model_Workout("1", "Squat", "12-15 reps per sets",
                        R.drawable.squat_video, R.drawable.squat_image,3,"12-15 reps",0));

                modelArrayList.add(new model_Workout("2", "Bench Press", "12-15 reps per sets",
                        R.drawable.barbell_bench_press_video, R.drawable.barbell_bench_press_image,3,"12-15 reps",0));

                modelArrayList.add(new model_Workout("3", " Inclined Bench Press", "12-15 reps per sets",
                        R.drawable.incline_bench_press_video, R.drawable.incline_bench_press_image,3,"12-15 reps",0));

                modelArrayList.add(new model_Workout("4", "Lat pull down", "12-15 reps per sets",
                        R.drawable.lat_pulldowns_video, R.drawable.lat_pulldowns_image,3,"12-15 reps",0));

                modelArrayList.add(new model_Workout("5", "Shoulder Press", "12-15 reps per sets",
                        R.drawable.shoulder_press_video, R.drawable.shoulder_press_image,3,"12-15 reps",0));

                modelArrayList.add(new model_Workout("6", "Lateral Raise", "12-15 reps per sets",
                        R.drawable.lateral_raise_video, R.drawable.lateral_raise_image,3,"12-15 reps",0));
                break;



            case "FRI":
                modelArrayList.add(new model_Workout(
                        "1", "Treadmill","30 mins",
                        R.drawable.treadmill_video, R.drawable.treadmill_image, 1, "", 1800000));
                modelArrayList.add(new model_Workout(
                        "2","Elliptical Cross Trainer","30 mins",
                        R.drawable.elliptical_machine_video,R.drawable.elliptical_machine_image,1,"",1800000));
                break;


            case "SAT":
                modelArrayList.add(new model_Workout("1", "Situps ", "30 sec per set",
                        R.drawable.sit_up_video, R.drawable.sit_up_image,3,"",30000));
                modelArrayList.add(new model_Workout("2", "Mountain Climbers", "30 sec per set",
                        R.drawable.mountain_climber_video, R.drawable.mountain_climber_image,3,"",30000));
                modelArrayList.add(new model_Workout("3", "Jumping Jacks ", "30 sec per set",
                        R.drawable.jumping_jack_video, R.drawable.jumping_jack_image,3,"",30000));
                break;
        }
        rcAdapterHome = new rcAdapter_TodaysWorkout(getContext(), modelArrayList,isRestDay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rcAdapterHome);
    }
}
