package com.jtdev.shape_shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jtdev.shape_shift.R;
import com.jtdev.shape_shift.class_bmiCalculator;

public class act_bmiResult extends AppCompatActivity {

    Button mrecalculatebmi, seePrograms;

    TextView mbmidisplay, mbmicateogory, mgender;
    Intent intent;
    ImageView mimageview;
    String mbmi;
    float intbmi;
    String height;
    String weight;
    float intheight, intweight;
    RelativeLayout mbackground;
    class_bmiCalculator calculator;
    String recom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bmi_result);

        //initialization of widgets
        mbmicateogory = findViewById(R.id.bmicategory);
        mrecalculatebmi = findViewById(R.id.recalculatebmi);
        seePrograms = findViewById(R.id.seeProgram);
        mbmidisplay = findViewById(R.id.bmidisplay);
        mgender = findViewById(R.id.genderdisplay);
        mbackground = findViewById(R.id.contentlayout);

        mimageview = findViewById(R.id.imageview);
        mrecalculatebmi = findViewById(R.id.recalculatebmi);

        //instantiate calculator class
        calculator = new class_bmiCalculator();

        //get the extra from bmiCalculator
        Intent intent = getIntent();
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        recom = intent.getStringExtra("r");


        if (recom != null) {
            recom = recom.trim().toLowerCase();

            if (recom.contains("underweight")) {
                mimageview.setImageResource(R.drawable.ic_underweight);
            } else if (recom.contains("regular")) {
                mimageview.setImageResource(R.drawable.ic_normal);
            } else if (recom.contains("overweight")) {
                mimageview.setImageResource(R.drawable.ic_overweight);
            } else if (recom.contains("obese")) {
                mimageview.setImageResource(R.drawable.ic_obese);
            } else {
                // Default or error image
                mimageview.setImageResource(R.drawable.ic_notification_lightorange);
            }
        }

        //set textview recom
        mbmicateogory.setText(recom);

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        float bmi = calculator.calculateBMI(intheight, intweight);

        //float bmi converted to string
        mbmi = Float.toString(bmi);

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(String.format("%.2f", bmi));

        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_bmiResult.this, act_bmiCalculator.class));
                finish();
            }
        });

        seePrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(act_bmiResult.this, act_dashBoard.class));

            }
        });
    }

    public String userWeight(float bmi) {
        if (bmi < 18.5) {
            return "underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "normal";
        } else if (bmi >= 25 && bmi < 30) {
            return "overweight";
        } else {
            return "obese";
        }
    }
}
