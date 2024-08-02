package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.model.model_Workout;

public class act_viewWorkout extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextView workNumber, workTitle, workTimer, workSetsDetail, workSetsRemaining,displayNumberOfSets,symbolX;
    ImageView workAnim;
    Button btnStart, btnDone;
    ProgressBar circularTimer;
    CountDownTimer countDownTimer;
    int workoutDuration;
    final int timerInterval = 10;
    int giph, placeholder, timer, numberOfSet;
    String title, number, des, numberOfRepetition,rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_workout);

        //init firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //init
        workNumber = findViewById(R.id.tvNumber);
        workTitle = findViewById(R.id.tvTitle);
        workAnim = findViewById(R.id.ivAnimation);
        workTimer = findViewById(R.id.timer);
        btnStart = findViewById(R.id.start);
        circularTimer = findViewById(R.id.circularTimer);
        workSetsDetail = findViewById(R.id.tv_displaySets);
        workSetsRemaining = findViewById(R.id.setRemaining);
        btnDone = findViewById(R.id.btnDone);
        displayNumberOfSets = findViewById(R.id.displayNumberOfSets);
        symbolX = findViewById(R.id.symbolX);


        Intent intent = getIntent();
        //extract
        giph = intent.getIntExtra("giph", 0);
        placeholder = intent.getIntExtra("placeholder", 0);
        rest = intent.getStringExtra("rest");
        title = intent.getStringExtra("title");
        number = intent.getStringExtra("number");
        des = intent.getStringExtra("description");
        numberOfSet = intent.getIntExtra("sets", 0);
        numberOfRepetition = intent.getStringExtra("repetition");
        timer = intent.getIntExtra("timer", 0);

        //sets
        workAnim.setImageResource(placeholder);
        workNumber.setText(String.valueOf(number));
        workTitle.setText(title);
        workoutDuration = timer;

        if (timer == 0) {

            //gone
            circularTimer.setVisibility(View.GONE);
            btnStart.setVisibility(View.GONE);
            workTimer.setVisibility(View.GONE);
            workSetsRemaining.setVisibility(View.GONE);


            //visible
            Glide.with(this)
                    .load(giph)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(workAnim);
            displayNumberOfSets.setText("Sets: "+numberOfSet);
            btnDone.setVisibility(View.VISIBLE);
            workAnim.setImageResource(giph);

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveWorkoutData();
                    startActivity(new Intent(act_viewWorkout.this, act_dashBoard.class));
                }
            });
        } else if(timer>0&&numberOfSet>0){
            //gone
            workSetsDetail.setVisibility(View.GONE);
            btnDone.setVisibility(View.GONE);
            symbolX.setVisibility(View.GONE);
            displayNumberOfSets.setVisibility(View.GONE);

            //visible
            circularTimer.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            workSetsRemaining.setText(String.format("SETS REMAINING: %d", numberOfSet));
            stopGif();
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startWorkoutTimer();
                }
            });


        }


    }

    private void startWorkoutTimer() {
        if (timer > 0) {
            circularTimer.setMax(workoutDuration);
            circularTimer.setProgress(workoutDuration);
            Glide.with(this)
                    .load(giph)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(workAnim);

            countDownTimer = new CountDownTimer(workoutDuration, timerInterval) {
                public void onTick(long millisUntilFinished) {
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    workTimer.setText(String.format("%02d:%02d", minutes, seconds));
                    circularTimer.setProgress((int) millisUntilFinished);
                }

                public void onFinish() {
                    stopGif();
                    workTimer.setText("Done");
                    circularTimer.setProgress(0);
                    numberOfSet--;

                    if (numberOfSet == 0) {
                        workSetsRemaining.setText(String.format("Sets remaining: %d", numberOfSet));
                        btnStart.setText("Next");
                        btnStart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveWorkoutData();
                                startActivity(new Intent(act_viewWorkout.this, act_dashBoard.class));
                            }
                        });
                    } else {
                        workSetsRemaining.setText(String.format("Sets remaining: %d", numberOfSet));
                    }
                }
            }.start();
        }
    }



    private void stopGif() {
        Glide.with(this).load(placeholder).into(workAnim);
    }

    private void saveWorkoutData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference userNode = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            String key = userNode.child("doneWorkout").push().getKey();

            model_Workout modelWorkout = new model_Workout(number, title, des, giph, placeholder, numberOfSet, numberOfRepetition, timer);

            if (key != null) {
                userNode.child("doneWorkout").child(key).setValue(modelWorkout);
            }
        }
    }



}
