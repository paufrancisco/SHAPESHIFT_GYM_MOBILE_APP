package com.jtdev.shape_shift.model;

public class model_Plan {

    public String date;
    public String typeOfWorkout;
    public int background;
    public String totalMins;
    public String numberOfExercise;


    public model_Plan(String date,String typeOfWorkout, int background, String totalMins, String numberOfExercise) {
        this.date = date;
        this.background = background;
        this.totalMins = totalMins;
        this.numberOfExercise = numberOfExercise;
        this.typeOfWorkout = typeOfWorkout;
    }

    public model_Plan() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeOfWorkout() {
        return typeOfWorkout;
    }

    public void setTypeOfWorkout(String typeOfWorkout) {
        this.typeOfWorkout = typeOfWorkout;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getTotalMins() {
        return totalMins;
    }

    public void setTotalMins(String totalMins) {
        this.totalMins = totalMins;
    }

    public String getNumberOfExercise() {
        return numberOfExercise;
    }

    public void setNumberOfExercise(String numberOfExercise) {
        this.numberOfExercise = numberOfExercise;
    }
}
