package com.jtdev.shape_shift.model;

public class model_Workout {

    public String workoutNumber;
    public String workoutTitle;
    public String workoutDescription,workRepetition;
    public int workoutGIPH;

    public int workPlaceholder;
    public int workSets,workTimer;
    public String workdetails;
    public String rest;

    public model_Workout(String workoutNumber, String workoutTitle, String workoutDescription,String rest) {
        this.workoutNumber = workoutNumber;
        this.workoutTitle = workoutTitle;
        this.workoutDescription = workoutDescription;
        this.rest = rest;
    }

    public model_Workout() {
    }

    public model_Workout(String workoutNumber, String workoutTitle, String workoutDescription, int workoutGIPH, int workPlaceholder, int workSets, String workRepetition, int workTimer) {

        this.workoutNumber = workoutNumber;
        this.workoutTitle = workoutTitle;
        this.workoutDescription = workoutDescription;
        this.workoutGIPH = workoutGIPH;
        this.workPlaceholder = workPlaceholder;
        this.workSets = workSets;
        this.workRepetition = workRepetition;
        this.workTimer = workTimer;
    }

    public model_Workout(String workoutNumber, String workoutTitle, int workoutGIPH, String workdetails) {
        this.workoutNumber = workoutNumber;
        this.workoutTitle = workoutTitle;
        this.workoutGIPH = workoutGIPH;
        this.workdetails = workdetails;
    }

    public String getWorkoutNumber() {
        return workoutNumber;
    }

    public void setWorkoutNumber(String workoutNumber) {
        this.workoutNumber = workoutNumber;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }

    public void setWorkoutTitle(String workoutTitle) {
        this.workoutTitle = workoutTitle;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }

    public int getWorkoutGIPH() {
        return workoutGIPH;
    }

    public void setWorkoutGIPH(int workoutGIPH) {
        this.workoutGIPH = workoutGIPH;
    }

    public int getWorkPlaceholder() {
        return workPlaceholder;
    }

    public void setWorkPlaceholder(int workPlaceholder) {
        this.workPlaceholder = workPlaceholder;
    }

    public int getWorkSets() {
        return workSets;
    }

    public void setWorkSets(int workSets) {
        this.workSets = workSets;
    }
}
