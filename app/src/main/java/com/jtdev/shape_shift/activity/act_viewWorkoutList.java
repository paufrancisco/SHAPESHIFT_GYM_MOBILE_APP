package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.adapter.rcAdapter_WorkoutList;
import com.jtdev.shape_shift.model.model_Workout;

import java.util.ArrayList;

public class act_viewWorkoutList extends AppCompatActivity {
    ArrayList<model_Workout> modelWorkouts;
    RecyclerView recyclerView;
    rcAdapter_WorkoutList rcAdapterWorkoutList;
    ImageView bg;
    TextView minutes,numberOfExercise;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_workout_list);

        bg = findViewById(R.id.imgBackground);
        minutes = findViewById(R.id.tvTotalMinutes);
        recyclerView = findViewById(R.id.rcWorkoutList);
        numberOfExercise = findViewById(R.id.tvNumberOfWorkouts);




        Intent intent = getIntent();
        String totalMinutes = intent.getStringExtra("totalMinutes");
        int background = intent.getIntExtra("background", 0);
        String numberOfWorkOuts = intent.getStringExtra("numberOfWorkouts");
        numberOfExercise.setText(numberOfWorkOuts);


        bg.setImageResource(background);
        minutes.setText(totalMinutes);

        day = intent.getStringExtra("date");

        if (day != null) {
            fetchHealthStatusFromFirebase(day);
        } else {

        }

    }

    private void fetchHealthStatusFromFirebase(String currentDay) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference usersData = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(userId).child("user_data").child("program");

            usersData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String program1 = snapshot.getValue(String.class);
                        if ("Gain Weight".equalsIgnoreCase(program1)) {
                            fetchUnderWeight(day);
                        } else if ("Maintain Healthy".equalsIgnoreCase(program1)) {
                            fetchNormal(day);
                        } else if ("Lose Weight".equalsIgnoreCase(program1)) {
                            fetchOverweight(day);
                        } else if ("Significant Weight Lose".equalsIgnoreCase(program1)) {
                            fetchObese(day);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void fetchUnderWeight(String day) {
        modelWorkouts = new ArrayList<>();
        switch (day.toUpperCase()) {
            case "MON":
                modelWorkouts.add(new model_Workout("1", "Surrenders", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Push Ups", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Inclined Push Up", R.drawable.incline_bench_press_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Body Weight Squat", R.drawable.bodyweight_squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                break;
            case "TUE":
                modelWorkouts.add(new model_Workout("1", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Crunches", R.drawable.crunches_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Flutter Kicks", R.drawable.flutter_kicks_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Leg Raise", R.drawable.leg_raise_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Burpees", R.drawable.burpees_video, "12 reps • 3 sets"));
                break;
            case "WED":
                modelWorkouts.add(new model_Workout("1", "Push Ups", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Body Weight Squats", R.drawable.bodyweight_squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Body Weight Row", R.drawable.bodyweight_row_video, "12-15 reps • 3 sets"));
                break;
            case "THU":
                modelWorkouts.add(new model_Workout("1", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Russian Twist", R.drawable.russian_twist_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Planking", R.drawable.planking_video, "45 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Mountain Climbers",R.drawable.mountain_climber_video, "30 seconds"));
                modelWorkouts.add(new model_Workout("5", "Leg Raise", R.drawable.leg_raise_video,"30 seconds"));

                break;
            case "FRI":
                modelWorkouts.add(new model_Workout("1", "Push Ups", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Inclined Push Up", R.drawable.incline_bench_press_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Body Squat", R.drawable.bodyweight_squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Mountain Climbers", R.drawable.mountain_climber_video, "30 sec • 3 sets"));
                break;
            case "SAT","SUN" :
                modelWorkouts.add(new model_Workout("1", "Boost muscle repair", R.drawable.ic_shower, "Take a cold shower"));
                modelWorkouts.add(new model_Workout("2", "Stretching", R.drawable.ic_stretch, "Stretch your legs and arms for 10 minutes"));
                modelWorkouts.add(new model_Workout("3", "Rest and Recover", R.drawable.ic_rest, "Take time to relax and let your body recover"));

                break;
            default:

                break;
        }
        setupRecyclerView();
    }

    public void fetchNormal(String day) {
        modelWorkouts = new ArrayList<>();
        switch (day.toUpperCase()) {
            case "MON":
                modelWorkouts.add(new model_Workout("1", "Diamond Push Up", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Squat", R.drawable.squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Push Up", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Pull Up", R.drawable.lat_pulldowns_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Burpees", R.drawable.burpees_video, "12-15 reps • 3 sets"));
                break;
            case "TUE":
                modelWorkouts.add(new model_Workout("1", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Crunches", R.drawable.crunches_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Leg Raise", R.drawable.leg_raise_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Planking", R.drawable.planking_video, "45 sec • 3 sets"));
                break;
            case "WED":
                modelWorkouts.add(new model_Workout("1", "Squats", R.drawable.squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Jogging", R.drawable.jogging_video, "1 hour"));
                modelWorkouts.add(new model_Workout("3", "Air Squat", R.drawable.air_squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Front Leg Raise", R.drawable.leg_raise_video, "12-15 reps • 3 sets"));
                break;
            case "THU":
                modelWorkouts.add(new model_Workout("1", "Crunches", R.drawable.crunches_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Mountain Climbers", R.drawable.mountain_climber_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Russian Twist", R.drawable.russian_twist_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Leg Raise", R.drawable.leg_raise_video, "30 sec • 3 sets"));
                break;
            case "FRI","SAT","SUN" :
                modelWorkouts.add(new model_Workout("1", "Boost muscle repair", R.drawable.ic_shower, "Take a cold shower"));
                modelWorkouts.add(new model_Workout("2", "Stretching", R.drawable.ic_stretch, "Stretch your legs and arms for 10 minutes"));
                modelWorkouts.add(new model_Workout("3", "Rest and Recover", R.drawable.ic_rest, "Take time to relax and let your body recover"));

                break;
            default:

                break;
        }
        setupRecyclerView();
    }

    public void fetchOverweight(String day) {
        modelWorkouts = new ArrayList<>();
        switch (day.toUpperCase()) {
            case "MON":
                modelWorkouts.add(new model_Workout("1", "Push Up", R.drawable.push_up_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Diamond Pushup", R.drawable.diamond_push_ups_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Squat", R.drawable.squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Burpees", R.drawable.burpees_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Inclined Push Up", R.drawable.incline_bench_press_video, "12-15 reps • 3 sets"));
                break;
            case "TUE", "SUN", "THU":
                modelWorkouts.add(new model_Workout("1", "Boost muscle repair", R.drawable.ic_shower, "Take a cold shower"));
                modelWorkouts.add(new model_Workout("2", "Stretching", R.drawable.ic_stretch, "Stretch your legs and arms for 10 minutes"));
                modelWorkouts.add(new model_Workout("3", "Rest and Recover", R.drawable.ic_rest, "Take time to relax and let your body recover"));

                break;
            case "WED":
                modelWorkouts.add(new model_Workout("1", "Squat", R.drawable.squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Air Squat", R.drawable.air_squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Front Leg Raise", R.drawable.leg_raise_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Jumping Jacks", R.drawable.jumping_jack_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Walking", R.drawable.walking_video, "30 min - 1 hour"));
                break;

            case "FRI":
                modelWorkouts.add(new model_Workout("1", "Cardio", R.drawable.cardio_video, "30 min - 1 hour"));
                modelWorkouts.add(new model_Workout("2", "Jumping Jacks", R.drawable.jumping_jack_video, "30 min - 1 hour"));
                modelWorkouts.add(new model_Workout("3", "Walking", R.drawable.walking_video, "1 hour"));
                break;
            case "SAT":
                modelWorkouts.add(new model_Workout("1", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Crunches", R.drawable.crunches_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Planking", R.drawable.planking_video, "45 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Russian Twist", R.drawable.russian_twist_video, "30 sec • 3 sets"));
                break;

            default:

                break;
        }
        setupRecyclerView();
    }

    public void fetchObese(String day) {
        modelWorkouts = new ArrayList<>();
        switch (day.toUpperCase()) {
            case "MON":
                modelWorkouts.add(new model_Workout("1", "Walking", R.drawable.walking_video, "1 hour"));
                modelWorkouts.add(new model_Workout("2", "Brisk Walking", R.drawable.brisk_walking_video, "1 hour"));
                modelWorkouts.add(new model_Workout("3", "Cardio", R.drawable.cardio_video, "1 hour"));
                break;
            case "TUE", "SUN", "THU":
                modelWorkouts.add(new model_Workout("1", "Boost muscle repair", R.drawable.ic_shower, "Take a cold shower"));
                modelWorkouts.add(new model_Workout("2", "Stretching", R.drawable.ic_stretch, "Stretch your legs and arms for 10 minutes"));
                modelWorkouts.add(new model_Workout("3", "Rest and Recover", R.drawable.ic_rest, "Take time to relax and let your body recover"));
                break;
            case "WED":
                modelWorkouts.add(new model_Workout("1", "Squat", R.drawable.squat_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Bench Press", R.drawable.bench_push_ups_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Incline Bench Press", R.drawable.incline_bench_press_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("4", "Lat Pull Down", R.drawable.lat_pulldowns_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("5", "Shoulder Press", R.drawable.shoulder_press_video, "12-15 reps • 3 sets"));
                modelWorkouts.add(new model_Workout("6", "Lateral Raise", R.drawable.lateral_raise_video, "12-15 reps • 3 sets"));
                break;
            case "FRI":
                modelWorkouts.add(new model_Workout("1", "Treadmill", R.drawable.treadmill_video, "30 min"));
                modelWorkouts.add(new model_Workout("2", "Elliptical Cross Trainer", R.drawable.elliptical_machine_video, "30 min"));
                break;
            case "SAT":
                modelWorkouts.add(new model_Workout("1", "Situps", R.drawable.sit_up_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("2", "Mountain Climbers", R.drawable.mountain_climber_video, "30 sec • 3 sets"));
                modelWorkouts.add(new model_Workout("3", "Crunches", R.drawable.crunches_video, "30 sec • 3 sets"));
                break;
            default:
                break;
        }
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        rcAdapterWorkoutList = new rcAdapter_WorkoutList(this, modelWorkouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rcAdapterWorkoutList);
    }
}
