package com.jtdev.shape_shift;

public class class_bmiCalculator {

    public float calculateBMI(float height, float weight){
        float heightInMeters = height / 100.0f;
        return weight / (heightInMeters * heightInMeters);
    }

    public String getRecommendation(float bmi) {
        if (bmi < 18.5) {
            return "     You are UNDERWEIGHT. It's important to maintain a balanced diet. " +
                    "We suggest you to try gain weight with proper guidance using our workout plan.";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "     Your weight is within a HEALTHY range. Keep up the good work and make sure you get frequent exercise and a healthy diet. " +
                    "Try our training regimen to keep a regular body.";
        } else if (bmi >= 25 && bmi < 30) {
            return "     You are OVERWEIGHT. To get a healthy weight, " +
                    "think about changing your lifestyle by consuming healthier food and engaging in more physical activity. " +
                    "Try our exercise program to start your new lifestyle.";
        } else {
            return "     You are OBESE. It's critical to put your health first by making lifestyle adjustments. " +
                    "Using our exercise program can help you lower the health risks linked to obesity.";
        }
    }

    public String getProgram(float bmi) {
        if (bmi < 18.5) {
            return "Gain Weight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Maintain Healthy";
        } else if (bmi >= 25 && bmi < 30) {
            return "Lose Weight";
        } else {
            return "Significant Weight Loss";
        }
    }
}
