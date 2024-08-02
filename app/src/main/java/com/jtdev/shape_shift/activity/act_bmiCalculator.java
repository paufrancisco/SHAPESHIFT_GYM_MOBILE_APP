package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.class_bmiCalculator;
import com.jtdev.shape_shift.model.model_User;

public class act_bmiCalculator extends AppCompatActivity {

    Button mcalculatebmi;
    String program;
    TextView mcurrentheight;
    TextView mcurrentage, mcurrentweight;
    ImageView mincrementage, mincrementweight, mdecrementweight, mdecrementage;
    SeekBar mseekbarforheight;
    RelativeLayout mmale, mfemale;
    int floatweight = 70;
    int intage = 30;
    int currentprogress;
    String mintprogress = "170";
    String typeofuser = "0";
    String weight2 = "40";
    String age2 = "30";

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    class_bmiCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bmi_calculator);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mcalculatebmi = findViewById(R.id.calculatebmi);
        mcurrentage = findViewById(R.id.currentage);
        mcurrentweight = findViewById(R.id.currentweight);
        mcurrentheight = findViewById(R.id.currentheight);
        mincrementage = findViewById(R.id.incrementage);
        mdecrementage = findViewById(R.id.decrementage);
        mincrementweight = findViewById(R.id.incrementweight);
        mdecrementweight = findViewById(R.id.decrementweight);
        mseekbarforheight = findViewById(R.id.seekbarforheight);
        mmale = findViewById(R.id.male);
        mfemale = findViewById(R.id.female);

        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_malefemalefocus));
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_malefemalenotfocus));
                typeofuser = "Male";
            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_malefemalefocus));
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_malefemalenotfocus));
                typeofuser = "Female";
            }
        });

        int max = 300;
        int min = 120;

        mseekbarforheight.setMax(max-min);

        int defaultprogress = 210-min;
        mseekbarforheight.setProgress(defaultprogress);

        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int adjustedprogress =  i+min;
                currentprogress = adjustedprogress;
                mintprogress = String.valueOf(adjustedprogress);
                mcurrentheight.setText(mintprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mincrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage = intage + 1;
                age2 = String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mdecrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage = intage - 1;
                age2 = String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatweight = floatweight + 1;
                weight2 = String.valueOf(floatweight);
                mcurrentweight.setText(weight2);
            }
        });

        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatweight = floatweight - 1;
                weight2 = String.valueOf(floatweight);
                mcurrentweight.setText(weight2);
            }
        });

        final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mcalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeofuser.equals("0")) {
                    Toast.makeText(act_bmiCalculator.this, "Please select your gender first", Toast.LENGTH_SHORT).show();
                } else if (mintprogress.equals("0")) {
                    Toast.makeText(act_bmiCalculator.this, "Select your height first", Toast.LENGTH_SHORT).show();
                } else if (intage == 0 || intage < 0) {
                    Toast.makeText(act_bmiCalculator.this, "Age is incorrect", Toast.LENGTH_SHORT).show();
                } else if (floatweight == 0 || floatweight < 0) {
                    Toast.makeText(act_bmiCalculator.this, "Weight is incorrect", Toast.LENGTH_SHORT).show();
                } else {

                    float heightInCm = Float.parseFloat(mintprogress);
                    if (heightInCm == 0) {
                        Toast.makeText(act_bmiCalculator.this, "Height cannot be zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    calculator = new class_bmiCalculator();

                    float bmi = calculator.calculateBMI(heightInCm, floatweight);
                    if (bmi < 18.5) {
                        program = "Gain Weight";
                    } else if (bmi >= 18.5 && bmi < 25) {
                        program = "Maintain Healthy";
                    } else if (bmi >= 25 && bmi < 30) {
                        program = "Lose Weight";
                    } else {
                        program = "Significant Weight Loss";
                    }


                    if (Float.isNaN(bmi) || Float.isInfinite(bmi)) {
                        Toast.makeText(act_bmiCalculator.this, "Invalid BMI value", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //insert data to realtime database
                    model_User userData = new model_User(typeofuser, intage, heightInCm, floatweight, bmi, program); //model

                    usersRef.child(mUser.getUid()).child("user_data").setValue(userData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(act_bmiCalculator.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(act_bmiCalculator.this, "Failed to save data" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    Intent intent = new Intent(act_bmiCalculator.this, act_bmiResult.class);
                    intent.putExtra("gender", typeofuser);
                    intent.putExtra("height", mintprogress);
                    intent.putExtra("weight", weight2);
                    intent.putExtra("age", age2);
                    String rec = calculator.getRecommendation(bmi);
                    intent.putExtra("r",rec);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
