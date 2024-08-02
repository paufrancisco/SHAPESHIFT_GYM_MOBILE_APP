package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jtdev.shape_shift.R;

public class act_challengeInfo extends AppCompatActivity {

    TextView name, challengeinstruction, back;
    ImageView image;
    Button done;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_challenge_info);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        name = findViewById(R.id.challengeName);
        image = findViewById(R.id.challengeImage);
        back = findViewById(R.id.btn_back);
        done = findViewById(R.id.btn_done);
        challengeinstruction = findViewById(R.id.challengeInstruction);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        final String iname = intent.getStringExtra("name");
        int iimage = intent.getIntExtra("image", 0);
        int idifficulty = intent.getIntExtra("difficulty", 0);
        //Challenge challengeFragment = intent.getParcelableExtra("challenge_fragment_reference");

        name.setText(iname);
        image.setImageResource(iimage);

        if (iname.equals("3mins Plank")) {
            challengeinstruction.setText("Hold a plank position for 3 minutes.");
        } else if (iname.equals("10km Sprint")) {
            challengeinstruction.setText("Run 10 kilometers as fast as you can.");
        } else if (iname.equals("2mins Squat")) {
            challengeinstruction.setText("Perform squats continuously for 2 minutes.");
        } else if (iname.equals("Hydration")) {
            challengeinstruction.setText("Drink enough water throughout the day.");
        } else if (iname.equals("Back Strength")) {
            challengeinstruction.setText("Perform exercises to strengthen your back muscles.");
        } else if (iname.equals("20 Push ups")) {
            challengeinstruction.setText("Perform 20 push-ups with proper form.");
        } else if (iname.equals("20 Curl ups")) {
            challengeinstruction.setText("Perform 20 curl-ups (sit-ups) with proper form.");
        } else if (iname.equals("20 Leg Raise")) {
            challengeinstruction.setText("Perform 20 leg raises, lying flat on your back and lifting your legs towards the ceiling.");
        } else if (iname.equals("20km Sprint")) {
            challengeinstruction.setText("Run 20 kilometers as fast as you can.");
        } else if (iname.equals("3mins Squat")) {
            challengeinstruction.setText("Hold a squat position for 3 minutes.");
        } else if (iname.equals("100 Push ups")) {
            challengeinstruction.setText("Perform 100 push-ups with proper form.");
        } else if (iname.equals("Win Arm wrestling")) {
            challengeinstruction.setText("Compete in an arm-wrestling match and win against your opponent.");
        } else if (iname.equals("5mins Plank")) {
            challengeinstruction.setText("Hold a plank position for 5 minutes.");
        } else if (iname.equals("30km Sprint")) {
            challengeinstruction.setText("Run 30 kilometers as fast as you can.");
        } else if (iname.equals("Deadlift")) {
            challengeinstruction.setText("Lift heavy weights from the ground to a standing position, focusing on proper form and technique.");
        } else {

            challengeinstruction.setText("No instructions available.");
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    saveChallengeToFirebase(userId, iname, idifficulty);
                    Intent intent1 = new Intent(act_challengeInfo.this, act_dashBoard.class);
                    intent1.putExtra("goods","goods");
                    startActivity(intent1);
                } else {
                    // No user signed in, handle accordingly
                    Toast.makeText(act_challengeInfo.this, "Please sign in to save the challenge", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

    private void saveChallengeToFirebase(String userId, String challengeName, int difficulty) {
        String key = databaseReference.child(userId).child("challenges").push().getKey();
        databaseReference.child(userId).child("challenges").child(key).child("name").setValue(challengeName);
        databaseReference.child(userId).child("challenges").child(key).child("difficulty").setValue(difficulty);

        Toast.makeText(act_challengeInfo.this, "Achievement Added", Toast.LENGTH_SHORT).show();
    }
}
